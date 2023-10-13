# Flexi Locale 使用文档

在开始使用之前，建议你仔细阅读此文档，以便你能更好地了解它的作用方式与功能。

如果你的项目依然在使用 Groovy DSL 进行管理，推荐迁移到 Kotlin DSL。

在 Groovy DSL 中使用此插件发生的任何问题，我们都将不再负责排查和修复，并且在后期版本可能会完全不再支持 Groovy DSL。

注意：此文档中将不再详细介绍在 Groovy DSL 中的使用方法。

## 快速开始

我们推荐使用 [SweetDependency](https://github.com/HighCapable/SweetDependency) 来自动管理依赖版本。

以下是使用 `SweetDependency` 的装载方式。

> 配置文件

```yaml
plugins:
  com.highcapable.flexilocale:
    alias: flexi-locale
    version: +
```

> build.gradle.kts

```kotlin
plugins {
    // 装载方式 1
    autowire(libs.plugins.flexi.locale)
    // 装载方式 2
    autowire("flexi-locale")
}
```

以下是传统的装载方式。

打开你需要集成 `FlexiLocale` 插件项目的 `build.gradle.kts`。

> 示例如下

```kotlin
plugins {
    id("com.highcapable.flexilocale") version "<version>"
}
```

请将上述代码中的 `<version>` 替换为 [Release](https://github.com/BetterAndroid/FlexiLocale/releases) 中的最新版本， 请注意不要在后方加入 apply false。

上述配置完成后，运行一次 Gradle Sync。

不出意外的情况下，你将会得到自动生成的 `AppLocale` 类，`Locale` 前方的名称为你的项目名称，默认应该为 `App`。

## 功能配置

你可以对 `FlexiLocale` 进行配置来实现自定义和个性化功能。

`FlexiLocale` 为你提供了相对丰富的可自定义功能，下面是这些功能的说明与配置方法。

请在你的 `build.gradle.kts` 中添加 `flexiLocale` 方法块以开始配置 `FlexiLocale`。

`FlexiLocale` 依附于 `android` 方法块生成。

> 示例如下

```kotlin
android {
    flexiLocale {
        // 启用 FlexiLocale，设置为 false 将禁用所有功能
        isEnable = true
        // 自定义生成的目录路径
        // 你可以填写相对于当前项目的路径
        // 默认为 "build/generated/flexi-locale"
        // 建议将生成的代码放置于 "build" 目录下，因为生成的代码不建议去修改它
        generateDirPath = "build/generated/flexi-locale"
        // 自定义生成的包名
        // Android 项目默认使用 "android" 配置方法块中的 "namespace"
        // 你可以不进行设置，包名在一般情况下会自动进行匹配
        packageName = "com.example.mydemo"
        // 自定义生成的类名
        // 默认使用当前项目的名称 + "Locale"
        // 你可以不进行设置，类名在一般情况下会自动进行匹配
        className = "MyDemo"
        // 是否启用受限访问功能
        // 默认不启用，启用后将为生成的类和方法添加 "internal" 修饰符
        // 如果你的项目为工具库或依赖，通常情况下建议启用
        // 启用后可以防止其他开发者在引用你的库时调用到你的项目国际化字符串调用类发生问题
        isEnableRestrictedAccess = false
    }
}
```

如需在 Groovy DSL 中使用，请将所有变量的 `=` 改为空格，并删除 `Enable` 前方的 `is` 并将 `E` 小写即可。

## 使用示例

`FlexiLocale` 会自动扫描当前项目 `res/values` 目录中所有包含 `<string>...</string>` 的 XML 文件。

假设这是你当前项目的 `strings.xml`，分为 `default` 和 `zh-rCN` 两个目录。

> values/strings.xml

```xml
<resources>
    <string name="app_name">My App</string>
    <string name="say_hello">Hello %1$s</string>
</resources>
```

> values-zh-rCN/strings.xml

```xml
<resources>
    <string name="app_name">我的应用</string>
    <string name="say_hello">你好 %1$s</string>
</resources>
```

我们建议在 `Application` 中装载 `AppLocale`，这样你就可以在应用的任何地方进行调用。

> 示例如下

```kotlin
lateinit var locale: AppLocale

class App : Application() {

    override fun onCreate() {
        locale = AppLocale.attach(this)
    }
}
```

下面，你就可以直接去使用这些字符串了。

> 示例如下

```kotlin
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // 将设置标题为：
        // default: My App
        // zh-rCN: 我的应用
        actionBar.title = locale.appName
        // 将设置文本为：
        // default: Hello John
        // zh-rCN: 你好 John
        findViewById<TextView>(R.id.hello_text).text = locale.sayHello("John")
    }
}
```

Java 的使用方式只需要将调用的字符串方法名前加入 `get` 即可。

需要注意的是，当你修改了 `strings.xml` 等资源文件，你需要重新运行一次 Gradle Sync 以得到最新的生成结果。

### 动态刷新

如果用户动态地修改了系统语言，你可以使用 `AppLocale` 中的 `attach { dynamicResources }` 方法设置动态资源实例。

如果你是使用 `Context` 装载的 `AppLocale`，那么你无需进行任何操作。

请注意，如果 `Activity` 未自动重新启动，请在 `Activity` 中手动调用 `recreate` 才能看到语言改变后的结果。

## 问题反馈

如果你在使用 `FlexiLocale` 的过程中遇到了任何问题，你都可以随时在 GitHub 开启一个 `issues` 向我们反馈。