pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
plugins {
    id("com.highcapable.sweetdependency") version "1.0.3"
    id("com.highcapable.sweetproperty") version "1.0.4"
}
sweetDependency {
    isEnableVerboseMode = false
}
sweetProperty {
    global {
        sourcesCode {
            className = rootProject.name
            isEnableRestrictedAccess = true
        }
    }
    rootProject { all { isEnable = false } }
}
rootProject.name = "FlexiLocale"
include(":flexilocal-gradle-plugin")