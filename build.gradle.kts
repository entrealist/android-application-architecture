import org.gradle.kotlin.dsl.repositories

buildscript {

    repositories {
        mavenCentral()
        jcenter()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://maven.google.com") }

        mavenLocal()
        google()

    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
    }
}

allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven { url = uri("https://maven.google.com") }

        flatDir {
            dirs("libs")
        }
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}