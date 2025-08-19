# 更新日志

## 1.0.0 | 2023.10.13

- 首个版本提交至 Maven

## 1.0.1 | 2023.10.13

- 修复在使用 Kotlin on Android 插件的项目上找不到源码路径的问题

## 1.0.2 | 2025.08.19

- 修复在新版 Android Gradle Plugin 及 Android Studio/IDEA 中部署源码路径时的错误
  `removeContentEntry: removed content entry url 'build/generated/flexi-locale' still exists after removing`
- 新增 `sourceSetName` 方法，允许自定义要部署的源集名称