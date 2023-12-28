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
package com.highcapable.flexilocale.plugin.config.proxy

import com.highcapable.flexilocale.FlexiLocale
import com.highcapable.flexilocale.generated.FlexiLocaleProperties

/**
 * [FlexiLocale] 配置类接口类
 */
internal interface IFlexiLocaleConfigs {

    companion object {

        /**
         * 默认的生成目录路径
         *
         * "build/generated/[FlexiLocaleProperties.PROJECT_MODULE_NAME]"
         */
        internal const val DEFAULT_GENERATE_DIR_PATH = "build/generated/${FlexiLocaleProperties.PROJECT_MODULE_NAME}"
    }

    /** 是否启用插件 */
    val isEnable: Boolean

    /** 自定义生成的目录路径 */
    val generateDirPath: String

    /** 自定义生成的包名 */
    val packageName: String

    /** 自定义生成的类名 */
    val className: String

    /** 是否启用受限访问功能 */
    val isEnableRestrictedAccess: Boolean

    /**
     * 获取内部 [hashCode]
     * @return [Int]
     */
    fun innerHashCode() = "$isEnable$generateDirPath$packageName$className$isEnableRestrictedAccess".hashCode()
}