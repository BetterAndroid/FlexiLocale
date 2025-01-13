/*
 * FlexiLocale - An easy generation Android i18ns string call Gradle plugin.
 * Copyright (C) 2019 HighCapable
 * https://github.com/BetterAndroid/FlexiLocale
 *
 * Apache License Version 2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is created by fankes on 2023/10/10.
 */
@file:Suppress("DEPRECATION")

package com.highcapable.flexilocale.plugin.helper

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.api.BaseVariant
import com.highcapable.flexilocale.gradle.factory.get
import com.highcapable.flexilocale.plugin.config.proxy.IFlexiLocaleConfigs
import com.highcapable.flexilocale.plugin.generator.LocaleSourcesGenerator
import com.highcapable.flexilocale.plugin.generator.factory.LocaleChildMap
import com.highcapable.flexilocale.plugin.generator.factory.LocaleFileMap
import com.highcapable.flexilocale.plugin.generator.factory.LocaleStringMap
import com.highcapable.flexilocale.utils.debug.FError
import com.highcapable.flexilocale.utils.debug.FLog
import com.highcapable.flexilocale.utils.factory.toFile
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

/**
 * I18ns 分析工具类
 */
internal object LocaleAnalysisHelper {

    /** Android 的 Application 插件名称 */
    private const val APPLICATION_PLUGIN_NAME = "com.android.application"

    /** Android 的 Library 插件名称 */
    private const val LIBRARY_PLUGIN_NAME = "com.android.library"

    /** Kotlin 的 Android 插件名称 */
    private const val KT_ANDROID_PLUGIN_NAME = "org.jetbrains.kotlin.android"

    /** I18ns 代码生成实例 */
    private val generator = LocaleSourcesGenerator()

    /** 当前全部 I18ns 数据 (来自但不一定完全为 strings.xml) */
    private val mappedStrings: LocaleStringMap = mutableMapOf()

    /** 当前项目命名空间 */
    private var namespace = ""

    /** 当前项目资源目录数组 */
    private val resDirectories = mutableListOf<File>()

    /** 上次修改的 Hash Code */
    private var lastModifiedHashCode = 0

    /** 配置是否已被修改 */
    private var isConfigsModified = true

    /** 当前使用的配置实例 */
    private lateinit var configs: IFlexiLocaleConfigs

    /**
     * 开始分析当前项目
     * @param project 当前项目
     * @param configs 当前配置
     */
    internal fun start(project: Project, configs: IFlexiLocaleConfigs) {
        this.configs = configs
        if (!configs.isEnable) return
        checkingConfigsModified(project, configs)
        initializePlugins(project)
        val lastMappedStrings: LocaleStringMap = mutableMapOf()
        val lastResolveStrings: LocaleStringMap = mutableMapOf()
        resDirectories.takeIf { it.isNotEmpty() }?.allValuesDirs()?.forEach { (localeName, files) ->
            val stringXmls: LocaleChildMap = mutableMapOf()
            files.forEach { stringXmls.putAll(resolveStringXml(it)) }
            lastResolveStrings[localeName] = stringXmls
        } ?: return FLog.warn(
            "Unable to get the resources dir of $project, " +
                "please check whether there does not have a resources dir or is not an Android project"
        )
        lastResolveStrings.onEach { (localeName, strings) ->
            strings.forEach { (key, value) ->
                if (lastMappedStrings[key] == null) lastMappedStrings[key] = mutableMapOf()
                lastMappedStrings[key]?.set(localeName, value)
            }
        }.clear()
        val isFileModified = mappedStrings != lastMappedStrings
        if (!isFileModified && !isConfigsModified) return
        mappedStrings.clear()
        mappedStrings.putAll(lastMappedStrings)
        lastMappedStrings.clear()
        updateGeneration()
    }

    /**
     * 检查配置是否已被修改
     * @param project 当前项目
     * @param configs 当前配置
     */
    private fun checkingConfigsModified(project: Project, configs: IFlexiLocaleConfigs) {
        val fileHashCode = project.buildFile.takeIf { it.exists() }?.readText()?.hashCode() ?: -1
        isConfigsModified = fileHashCode == -1 || lastModifiedHashCode != fileHashCode || this.configs.innerHashCode() != configs.innerHashCode()
        lastModifiedHashCode = fileHashCode
    }

    /**
     * 初始化 Android Gradle plugin
     * @param project 当前项目
     */
    private fun initializePlugins(project: Project) {
        runCatching {
            fun BaseExtension.updateSourceDirs() = sourceSets.configureEach { kotlin.srcDir(configs.generateDirPath) }
            fun KotlinProjectExtension.updateSourceDirs() = sourceSets.configureEach { kotlin.srcDir(configs.generateDirPath) }
            fun BaseVariant.updateResDirectories() = sourceSets.forEach { provide -> provide.resDirectories?.also { resDirectories.addAll(it) } }
            project.plugins.withId(APPLICATION_PLUGIN_NAME) {
                project.get<AppExtension>().also { extension ->
                    namespace = extension.namespace ?: ""
                    extension.applicationVariants.forEach { variant ->
                        variant.updateResDirectories()
                    }; extension.updateSourceDirs()
                }
            }
            project.plugins.withId(LIBRARY_PLUGIN_NAME) {
                project.get<LibraryExtension>().also { extension ->
                    namespace = extension.namespace ?: ""
                    extension.libraryVariants.forEach { variant ->
                        variant.updateResDirectories()
                    }; extension.updateSourceDirs()
                }
            }
            project.plugins.withId(KT_ANDROID_PLUGIN_NAME) {
                project.get<KotlinAndroidProjectExtension>().also { extension ->
                    extension.updateSourceDirs()
                }
            }
        }.onFailure { FError.make("Failed to initialize Android Gradle plugin, this may be not or a wrong Android project\n$it") }
    }

    /** 更新生成后的代码内容 */
    private fun updateGeneration() {
        val packageName = "${configs.packageName.ifBlank { namespace }}.generated.locale"
        val generateDir = configs.generateDirPath.toFile().apply { if (exists() && isDirectory) deleteRecursively() }
        generator.build(configs, mappedStrings, namespace, packageName).writeTo(generateDir)
    }

    /**
     * 解析当前资源目录下的全部可用 values 目录数组 (包含 I18ns 数据)
     * @return [LocaleFileMap]
     */
    private fun List<File>.allValuesDirs(): LocaleFileMap {
        val valuesDirs: LocaleFileMap = mutableMapOf()
        forEach {
            it.listFiles()?.filter { dir -> dir.name.startsWith("values") }?.forEach eachDir@{ valuesDir ->
                if (!valuesDir.exists() || !valuesDir.isDirectory) return@eachDir
                val langName = if (valuesDir.name == "values") "default" else valuesDir.name.split("s-").getOrNull(1) ?: return@eachDir
                if (valuesDirs[langName] == null) valuesDirs[langName] = mutableSetOf()
                valuesDirs[langName]?.add(valuesDir)
            }
        }; return valuesDirs
    }

    /**
     * 解析当前资源目录下的全部 Xml 文件内容到键值对数组
     * @param valuesDir 当前资源目录
     * @return [LocaleChildMap]
     */
    private fun resolveStringXml(valuesDir: File): LocaleChildMap {
        val lastMappedStrings: LocaleChildMap = mutableMapOf()
        valuesDir.listFiles()?.filter { it.name.endsWith(".xml") }?.forEach {
            lastMappedStrings.putAll(it.readText().parseResourcesXml())
        }; return lastMappedStrings
    }

    /**
     * 解析资源 Xml 文件内容到键值对数组
     * @return [LocaleChildMap]
     */
    private fun String.parseResourcesXml(): LocaleChildMap {
        val builder = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = runCatching { builder.parse(byteInputStream()) }.getOrNull() ?: return mutableMapOf()
        val rootNode = document.documentElement
        if (rootNode.nodeName != "resources") return mutableMapOf()
        val nodes = rootNode.getElementsByTagName("string")
        val keyValues: LocaleChildMap = mutableMapOf()
        (0 until nodes.length).forEach { index ->
            val node = nodes.item(index)
            if (node.nodeType == Node.ELEMENT_NODE) {
                val element = node as Element
                val name = element.getAttribute("name")
                val content = element.textContent
                keyValues[name] = content
            }
        }; return keyValues
    }
}