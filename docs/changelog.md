# Changelog

## 1.0.0 | 2023.10.13

- The first version is submitted to Maven

## 1.0.1 | 2023.10.13

- Fixed a problem where the source code path could not be found on projects using the Kotlin on Android plugin

## 1.0.2 | 2025.08.19

- Fix errors when deploying source code paths in the new version of Android Gradle Plugin and Android Studio/IDEA
  `removeContentEntry: removed content entry url 'build/generated/flexi-locale' still exists after removing`
- Added the `sourceSetName` method to allow customization of the source set name to be deployed