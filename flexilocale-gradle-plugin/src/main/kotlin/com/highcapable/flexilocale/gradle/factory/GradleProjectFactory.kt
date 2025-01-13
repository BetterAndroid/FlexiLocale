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
 * This file is created by fankes on 2023/10/11.
 */
package com.highcapable.flexilocale.gradle.factory

import org.gradle.api.Project

/**
 * 获取指定项目的完整名称 (无子项目前冒号)
 * @return [String]
 */
internal fun Project.fullName(): String {
    val baseNames = mutableListOf<String>()

    /**
     * 递归子项目
     * @param project 当前项目
     */
    fun fetchChild(project: Project) {
        project.parent?.also { if (it != it.rootProject) fetchChild(it) }
        baseNames.add(project.name)
    }; fetchChild(project = this)
    return buildString { baseNames.onEach { append(":$it") }.clear() }.drop(1)
}