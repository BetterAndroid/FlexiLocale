/*
 * FlexiLocale - An easy generation Android i18ns string call Gradle plugin.
 * Copyright (C) 2019-2023 HighCapable
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
 * This file is created by fankes on 2023/10/13.
 */
package com.highcapable.flexilocale.plugin.generator.factory

import java.io.File

/** I18ns 数组类型定义 */
internal typealias LocaleStringMap = MutableMap<String, LocaleChildMap>

/** I18ns (子键值对) 数组类型定义 */
internal typealias LocaleChildMap = MutableMap<String, String>

/** I18ns (文件) 数组类型定义 */
internal typealias LocaleFileMap = MutableMap<String, MutableSet<File>>