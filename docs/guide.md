# Flexi Locale Documentation

Before you start using it, it is recommended that you read this document carefully so that you can better understand how it works and its functions.

If your project is still managed using Groovy DSL, it is recommended to migrate to Kotlin DSL.

We will no longer be responsible for troubleshooting and fixing any issues that occur with this plugin in Groovy DSL, and Groovy DSL support may be
dropped entirely in later releases.

Note: Usage in the Groovy DSL will not be detailed in this document.

## Quick Start

We recommend using [SweetDependency](https://github.com/HighCapable/SweetDependency) to autowire dependencies versions.

The following is the loading method using `SweetDependency`.

> Configuration File

```yaml
plugins:
  com.highcapable.flexilocale:
    alias: flexi-locale
    version: +
```

> build.gradle.kts

```kotlin
plugins {
    // Loading method 1
    autowire(libs.plugins.flexi.locale)
    // Loading method 2
    autowire("flexi-locale")
}
```

The following is the traditional loading method.

Open the `build.gradle.kts` project where you need to integrate the `FlexiLocale` plugin.

> The following example

```kotlin
plugins {
    id("com.highcapable.flexilocale") version "<version>"
}
```

Please replace `<version>` in the above code with the latest version in [Release](https://github.com/BetterAndroid/FlexiLocale/releases).

Please be careful not to add apply false at the end.

After the above configuration is completed, run Gradle Sync once.

If nothing else goes wrong, you will get the automatically generated `AppLocale` class,
the name in front of `Locale` is your project name, and the default should be `App`.

## Function Configuration

You can configure `FlexiLocale` to achieve customization and personalization.

`FlexiLocale` provides you with a relatively rich set of customizable functions.

The following is the description and configuration method of these functions.

Please add the `flexiLocale` method block to your `build.gradle.kts` to start configuring `FlexiLocale`.

`FlexiLocale` depends on the `android` method block generation.

> The following example

```kotlin
android {
    flexiLocale {
        // Enable FlexiLocale, setting to false will disable all functionality
        isEnable = true
        // Customize the generated directory path
        // You can fill in the path relative to the current project
        // Default is "build/generated/flexi-locale"
        // It is recommended to place the generated code in the "build" directory, because the generated code is not recommended to be modified
        generateDirPath = "build/generated/flexi-locale"
        // Customize the generated package name
        // Android projects use the "namespace" in the "android" configuration method block by default
        // You don't need to set it, the package name will be automatically matched under normal circumstances
        packageName = "com.example.mydemo"
        // Customize the generated class name
        // By default, the name of the current project + "Locale" is used
        // You don't need to set it, the class name will be automatically matched under normal circumstances
        className = "MyDemo"
        // Whether to enable restricted access function
        // Not enabled by default. When enabled, the "internal" modifier will be added to the generated classes and methods
        // If your project is a tool library or dependency, it is usually recommended to enable it
        // Once enabled, it can prevent other developers from calling your project's i18ns string calling classes when they reference your library
        isEnableRestrictedAccess = false
    }
}
```

If you want to use it in Groovy DSL, please change the `=` of all variables to spaces, delete the `is` in front of `Enable` and lowercase `E`.

## Usage Example

`FlexiLocale` will automatically scan all XML files containing `<string>...</string>` in the `res/values` directory of the current project.

Assume this is the `strings.xml` of your current project, divided into two directories: `default` and `zh-rCN`.

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

We recommend loading `AppLocale` in `Application` so you can call it from anywhere in your application.

> The following example

```kotlin
lateinit var locale: AppLocale

class App : Application() {

    override fun onCreate() {
        locale = AppLocale.attach(this)
    }
}
```

Next, you can use these strings directly.

> The following example

```kotlin
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Will set the title to:
        // default: My App
        // zh-rCN: 我的应用
        actionBar.title = locale.appName
        // Will set the text to:
        // default: Hello John
        // zh-rCN: 你好 John
        findViewById<TextView>(R.id.hello_text).text = locale.sayHello("John")
    }
}
```

To use Java, you only need to add `get` before the name of the string method to be called.

It should be noted that when you modify resource files such as `strings.xml`, you need to re-run Gradle Sync to get the latest generation results.

### Dynamic Refresh

If the user dynamically changes the system language, you can use the `attach { dynamicResources }` method in `AppLocale` to set a dynamic resources
instance.

If you loaded `AppLocale` using `Context`, then you don't need to do anything.

Please note that if the `Activity` is not restarted automatically, please manually call `recreate` in the `Activity` to see the results of the
language change.

## Feedback

If you encounter any problems while using `FlexiLocale`, you can always open an `issues` on GitHub to give us feedback.