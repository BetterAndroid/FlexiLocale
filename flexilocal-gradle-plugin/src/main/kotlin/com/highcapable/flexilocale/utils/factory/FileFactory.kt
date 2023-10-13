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
 * This file is created by fankes on 2023/10/11.
 */
@file:Suppress("unused")

package com.highcapable.flexilocale.utils.factory

import java.io.File

/**
 * 字符串路径转换为文件
 *
 * 自动调用 [parseFileSeparator]
 * @return [File]
 */
internal fun String.toFile() = File(parseFileSeparator())

/**
 * 格式化到当前操作系统的文件分隔符
 * @return [String]
 */
internal fun String.parseFileSeparator() = replace("/", File.separator).replace("\\", File.separator)