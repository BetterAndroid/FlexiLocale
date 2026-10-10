# Flexi Locale

[![GitHub license](https://img.shields.io/github/license/BetterAndroid/FlexiLocale?color=blue&style=flat-square)](https://github.com/BetterAndroid/FlexiLocale/blob/master/LICENSE)
[![GitHub release](https://img.shields.io/github/v/release/BetterAndroid/FlexiLocale?display_name=release&logo=github&color=green&style=flat-square)](https://github.com/BetterAndroid/FlexiLocale/releases)
[![Telegram](https://img.shields.io/badge/discussion%20dev-Telegram-blue.svg?logo=telegram&style=flat-square)](https://t.me/BetterAndroid_Dev)
[![QQ](https://img.shields.io/badge/discussion%20dev-QQ-blue.svg?logo=tencent-qq&logoColor=red&style=flat-square)](https://qm.qq.com/cgi-bin/qm/qr?k=Pnsc5RY6N2mBKFjOLPiYldbAbprAU3V7&jump_from=webapi&authKey=X5EsOVzLXt1dRunge8ryTxDRrh9/IiW1Pua75eDLh9RE3KXE+bwXIYF5cWri/9lf)

<img src="img-src/icon.png" width = "100" height = "100" alt="LOGO"/>

一个自动为 Android 项目生成国际化字符串调用的 Gradle 插件。

[English](README.md) | 简体中文

| <img src="https://github.com/BetterAndroid/.github/blob/main/img-src/logo.png?raw=true" width = "30" height = "30" alt="LOGO"/> | [BetterAndroid](https://github.com/BetterAndroid) |
| ------------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------- |

这个项目属于上述组织，**点击上方链接关注这个组织**，发现更多好项目。

## 🚨 此项目已停止维护

此项目由于很多未能解决的黑盒问题已被弃用，我们不再推荐任何人使用它，并且它已不再支持 AGP 9+，整个项目后期会改名重构，敬请期待。

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

- [点击这里](docs/guide-zh-CN.md) 查看使用文档

## 更新日志

- [点击这里](docs/changelog-zh-CN.md) 查看历史更新日志

## Star History

![Star History Chart](https://api.star-history.com/svg?repos=BetterAndroid/FlexiLocale&type=Date)

## 许可证

- [Apache-2.0](https://www.apache.org/licenses/LICENSE-2.0)

```
Apache License Version 2.0

Copyright (C) 2019 HighCapable

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

版权所有 © 2019 HighCapable