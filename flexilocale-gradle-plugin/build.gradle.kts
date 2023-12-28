plugins {
    `kotlin-dsl`
    autowire(libs.plugins.kotlin.jvm)
    autowire(libs.plugins.maven.publish)
}

group = property.project.groupName
version = property.project.version

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    withSourcesJar()
}

kotlin {
    jvmToolchain(17)
    sourceSets.all { languageSettings { languageVersion = "2.0" } }
}

dependencies {
    compileOnly(com.android.library.com.android.library.gradle.plugin)
    compileOnly(org.jetbrains.kotlin.kotlin.gradle.plugin)
    implementation(com.squareup.kotlinpoet)
}

gradlePlugin {
    plugins {
        create(property.project.moduleName) {
            id = property.project.groupName
            implementationClass = property.gradle.plugin.implementationClass
        }
    }
}