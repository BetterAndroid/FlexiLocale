# Flexi Locale

[![GitHub license](https://img.shields.io/github/license/BetterAndroid/FlexiLocale?color=blue&style=flat-square)](https://github.com/BetterAndroid/FlexiLocale/blob/master/LICENSE)
[![GitHub release](https://img.shields.io/github/v/release/BetterAndroid/FlexiLocale?display_name=release&logo=github&color=green&style=flat-square)](https://github.com/BetterAndroid/FlexiLocale/releases)
[![Telegram](https://img.shields.io/badge/discussion-Telegram-blue.svg?logo=telegram&style=flat-square)](https://t.me/BetterAndroid)
[![Telegram](https://img.shields.io/badge/discussion%20dev-Telegram-blue.svg?logo=telegram&style=flat-square)](https://t.me/HighCapable_Dev)
[![QQ](https://img.shields.io/badge/discussion%20dev-QQ-blue.svg?logo=tencent-qq&logoColor=red&style=flat-square)](https://qm.qq.com/cgi-bin/qm/qr?k=Pnsc5RY6N2mBKFjOLPiYldbAbprAU3V7&jump_from=webapi&authKey=X5EsOVzLXt1dRunge8ryTxDRrh9/IiW1Pua75eDLh9RE3KXE+bwXIYF5cWri/9lf)

<img src="img-src/icon.png" width = "100" height = "100" alt="LOGO"/>

An easy generation Android i18ns string call Gradle plugin.

English | [简体中文](README-zh-CN.md)

| <img src="https://github.com/BetterAndroid/.github/blob/main/img-src/logo.png?raw=true" width = "30" height = "30" alt="LOGO"/> | [BetterAndroid](https://github.com/BetterAndroid) |
|---------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------|

This project belongs to the above-mentioned organization, **click the link above to follow this organization** and discover more good projects.

## What's this

This is a Gradle plugin for automatically generating i18ns string calling code functions for Android projects.

In Android projects, to use i18ns string, you need to define them in `strings.xml` and then call them using `context.getString(R.string.xxx)`, which
is very cumbersome and inflexible.

That's why this project was born.

With this plugin, you now only need to instantiate the `AppLocale` class generated by the plugin once, and then you can use it anywhere.

> Traditional Style

```kotlin
val appName = context.getString(R.string.app_name)
```

> Modern Style

```kotlin
val locale by lazy { AppLocale.attach(context) }
val appName = locale.appName
```

If you are still using Java, the writing method remains the same.

```java
var locale = AppLocale.attach(context);
var appName = locale.getAppName();
```

## Compatibility

The theory supports not very old Gradle, the recommended version is `7.x.x` and above.

Android projects containing Kotlin plugins are supported, other types of projects are not supported yet.

> Build Script Language

- Kotlin DSL

It is recommended to use this language as the build script language first, which is also the language currently recommended by Gradle.

- Groovy DSL

Some functions may be incompatible, support will be gradually dropped in the future, and some functions may become unavailable.

## Get Started

- [Click here](docs/guide.md) to view the documentation

## Changelog

- [Click here](docs/changelog.md) to view the historical changelog

## Promotion

<!--suppress HtmlDeprecatedAttribute -->
<div align="center">
     <h2>Hey, please stay! 👋</h2>
     <h3>Here are related projects such as Android development tools, UI design, Gradle plugins, Xposed Modules and practical software. </h3>
     <h3>If the project below can help you, please give me a star! </h3>
     <h3>All projects are free, open source, and follow the corresponding open source license agreement. </h3>
     <h1><a href="https://github.com/fankes/fankes/blob/main/project-promote/README.md">→ To see more about my projects, please click here ←</a></h1>
</div>

## Star History

![Star History Chart](https://api.star-history.com/svg?repos=BetterAndroid/FlexiLocale&type=Date)

## License

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

Copyright © 2019 HighCapable