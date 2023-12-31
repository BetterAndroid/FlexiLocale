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
 * This file is created by fankes on 2023/10/10.
 */
package com.highcapable.flexilocale.plugin

import com.highcapable.flexilocale.FlexiLocale
import com.highcapable.flexilocale.gradle.factory.get
import com.highcapable.flexilocale.gradle.factory.getOrCreate
import com.highcapable.flexilocale.gradle.proxy.IProjectLifecycle
import com.highcapable.flexilocale.plugin.extension.dsl.configure.FlexiLocaleConfigureExtension
import com.highcapable.flexilocale.plugin.helper.LocaleAnalysisHelper
import com.highcapable.flexilocale.utils.debug.FError
import org.gradle.api.Project

/**
 * [FlexiLocale] 插件扩展类
 */
internal class FlexiLocaleExtension internal constructor() : IProjectLifecycle {

    private companion object {

        /** Android Gradle plugin 扩展名称 */
        private const val ANDROID_EXTENSION_NAME = "android"
    }

    /** 当前配置方法体实例 */
    private var configure: FlexiLocaleConfigureExtension? = null

    override fun onLoaded(project: Project) {
        runCatching {
            configure = project.get(ANDROID_EXTENSION_NAME).getOrCreate<FlexiLocaleConfigureExtension>(FlexiLocaleConfigureExtension.NAME)
        }.onFailure { FError.make("Configure $project got an error, ${FlexiLocale.TAG} can only supports Android projects\nCaused by: $it") }
    }

    override fun onEvaluate(project: Project) {
        val configs = configure?.build(project) ?: FError.make("Extension \"${FlexiLocaleConfigureExtension.NAME}\" create failed")
        LocaleAnalysisHelper.start(project, configs)
    }
}