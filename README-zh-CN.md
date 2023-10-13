# Flexi Locale

[![GitHub license](https://img.shields.io/github/license/BetterAndroid/FlexiLocale?color=blue)](https://github.com/BetterAndroid/FlexiLocale/blob/master/LICENSE)
[![GitHub release](https://img.shields.io/github/v/release/BetterAndroid/FlexiLocale?display_name=release&logo=github&color=green)](https://github.com/BetterAndroid/FlexiLocale/releases)
[![Telegram](https://img.shields.io/badge/discussion-Telegram-blue.svg?logo=telegram)](https://t.me/BetterAndroid)
[![Telegram](https://img.shields.io/badge/discussion%20dev-Telegram-blue.svg?logo=telegram)](https://t.me/HighCapable_Dev)

<img src="https://github.com/BetterAndroid/FlexiLocale/blob/master/img-src/icon.png?raw=true" width = "100" height = "100" alt="LOGO"/>

一个自动为 Android 项目生成国际化字符串调用的 Gradle 插件。

[English](https://github.com/BetterAndroid/FlexiLocale/blob/master/README.md) | 简体中文

## 这是什么

这是一个用来自动为 Android 项目生成国际化字符串调用代码功能的 Gradle 插件。

在 Android 项目中，要使用国际化字符串，需要在 `strings.xml` 中进行定义，然后使用 `context.getString(R.string.xxx)` 的方式去调用，非常的繁琐和不灵活。

这就是这个项目诞生的原因，通过这个插件，你现在只需要实例化一次插件生成的 `AppLocale` 类，然后就可以在任意地方使用了。

> 传统写法

```kotlin
val appName = context.getString(R.string.app_name)
```

> 现代写法

```kotlin
val locale by lazy { AppLocale.attach(context) }
val appName = locale.appName
```

如果你依然在使用 Java，那么写法保持不变。

```java
var locale = AppLocale.attach(context);
var appName = locale.getAppName();
```

## 兼容性

理论支持不是很旧的 Gradle，建议版本为 `7.x.x` 及以上。

支持包含 Kotlin 插件的 Android 项目，其它类型的项目暂不支持。

> 构建脚本语言

- Kotlin DSL

推荐优先使用此语言作为构建脚本语言，这也是目前 Gradle 推荐的语言。

- Groovy DSL

部分功能可能无法兼容，在后期会逐渐放弃支持，且部分功能会无法使用。

## 开始使用

- [点击这里](https://github.com/BetterAndroid/FlexiLocale/blob/master/docs/guide.md) 查看使用文档

## 更新日志

- [点击这里](https://github.com/BetterAndroid/FlexiLocale/blob/master/docs/changelog.md) 查看历史更新日志

## 项目推广

如果你正在寻找一个可以自动管理 Gradle 项目依赖的 Gradle 插件，你可以了解一下 [SweetDependency](https://github.com/HighCapable/SweetDependency) 项目。

如果你正在寻找一个可以自动生成属性键值的 Gradle 插件，你可以了解一下 [SweetProperty](https://github.com/HighCapable/SweetProperty) 项目。

本项目同样使用了 **SweetDependency** 和 **SweetProperty**。

## Star History

![Star History Chart](https://api.star-history.com/svg?repos=BetterAndroid/FlexiLocale&type=Date)

## 许可证

- [Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0)

```
Apache License Version 2.0

Copyright (C) 2019-2023 HighCapable

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

版权所有 © 2019-2023 HighCapable