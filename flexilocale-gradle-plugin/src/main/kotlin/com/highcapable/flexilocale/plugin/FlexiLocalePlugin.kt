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
@file:Suppress("unused")

package com.highcapable.flexilocale.plugin

import com.highcapable.flexilocale.FlexiLocale
import com.highcapable.flexilocale.utils.debug.FError
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * [FlexiLocale] 插件定义类
 */
class FlexiLocalePlugin<T : ExtensionAware> internal constructor() : Plugin<T> {

    /** 当前扩展实例 */
    private val extension = FlexiLocaleExtension()

    override fun apply(target: T) = when (target) {
        is Project -> {
            extension.onLoaded(target)
            target.afterEvaluate { extension.onEvaluate(project = this) }
        }
        else -> FError.make("${FlexiLocale.TAG} can only applied in build.gradle or build.gradle.kts, but current is $target")
    }
}