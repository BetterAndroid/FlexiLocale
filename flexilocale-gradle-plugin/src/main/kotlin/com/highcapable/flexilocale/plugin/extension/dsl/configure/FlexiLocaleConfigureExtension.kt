/*
 * FlexiLocale - An easy generation Android i18ns string call Gradle plugin.
 * Copyright (C) 2019-2024 HighCapable
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
 * This file is created by fankes on 2023/10/11.
 */
@file:Suppress("MemberVisibilityCanBePrivate")

package com.highcapable.flexilocale.plugin.extension.dsl.configure

import com.highcapable.flexilocale.FlexiLocale
import com.highcapable.flexilocale.gradle.factory.fullName
import com.highcapable.flexilocale.plugin.config.proxy.IFlexiLocaleConfigs
import com.highcapable.flexilocale.utils.debug.FError
import com.highcapable.flexilocale.utils.factory.uppercamelcase
import org.gradle.api.Project

/**
 * [FlexiLocale] 配置方法体实现类
 */
open class FlexiLocaleConfigureExtension internal constructor() {

    internal companion object {

        /** [FlexiLocaleConfigureExtension] 扩展名称 */
        internal const val NAME = "flexiLocale"
    }

    /**
     * 是否启用插件
     *
     * 默认启用 - 如果你想关闭插件 - 在这里设置就可以了
     */
    var isEnable = true
        @JvmName("enable") set

    /**
     * 自定义生成的目录路径
     *
     * 你可以填写相对于当前项目的路径
     *
     * 默认为 [IFlexiLocaleConfigs.DEFAULT_GENERATE_DIR_PATH]
     */
    var generateDirPath = IFlexiLocaleConfigs.DEFAULT_GENERATE_DIR_PATH
        @JvmName("generateDirPath") set

    /**
     * 自定义生成的包名
     *
     * Android 项目默认使用 "android" 配置方法块中的 "namespace"
     */
    var packageName = ""
        @JvmName("packageName") set

    /**
     * 自定义生成的类名
     *
     * 默认使用当前项目的名称 + "Locale"
     */
    var className = ""
        @JvmName("className") set

    /**
     * 是否启用受限访问功能
     *
     * 默认不启用 - 启用后将为生成的类和方法添加 "internal" 修饰符
     */
    var isEnableRestrictedAccess = false
        @JvmName("enableRestrictedAccess") set

    /**
     * 构造 [IFlexiLocaleConfigs]
     * @param project 当前项目
     * @return [IFlexiLocaleConfigs]
     */
    internal fun build(project: Project): IFlexiLocaleConfigs {
        /** 检查合法包名 */
        fun String.checkingValidPackageName() {
            if (isNotBlank() && !matches("^[a-zA-Z_][a-zA-Z0-9_]*(\\.[a-zA-Z_][a-zA-Z0-9_]*)*$".toRegex()))
                FError.make("Invalid package name \"$this\"")
        }

        /** 检查合法类名 */
        fun String.checkingValidClassName() {
            if (isNotBlank() && !matches("^[a-zA-Z][a-zA-Z0-9_]*$".toRegex()))
                FError.make("Invalid class name \"$this\"")
        }
        packageName.checkingValidPackageName()
        className.checkingValidClassName()
        val currentEnable = isEnable
        val currentGenerateDirPath = project.file(generateDirPath).absolutePath
        val currentPackageName = packageName
        val currentClassName = "${className.ifBlank { project.fullName().uppercamelcase() }}Locale"
        val currentEnableRestrictedAccess = isEnableRestrictedAccess
        return object : IFlexiLocaleConfigs {
            override val isEnable get() = currentEnable
            override val generateDirPath get() = currentGenerateDirPath
            override val packageName get() = currentPackageName
            override val className get() = currentClassName
            override val isEnableRestrictedAccess get() = currentEnableRestrictedAccess
        }
    }
}