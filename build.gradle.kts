import org.gradle.kotlin.dsl.repositories

buildscript {
    project.apply {
        from("./dependences.gradle")
    }

    repositories {
        mavenCentral()
        jcenter()
        maven { url = uri("https://plugins.gradle.org/m2/") }
        maven { url = uri("https://maven.google.com") }
        maven { url = uri("https://maven.fabric.io/public") }

        mavenLocal()
        google()

    }

    dependencies {
        classpath("com.android.tools.build:gradle:${Versions.gradle}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}")
        classpath("io.fabric.tools:gradle:1.+")
        classpath("com.google.gms:google-services:${Versions.playServices}")
        classpath("com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersions}")
    }
}
allprojects {
    repositories {
        mavenCentral()
        jcenter()
        maven { url  = uri("https://maven.fabric.io/public") }
        maven { url  = uri("https://maven.google.com") }
        maven { url  = uri("https://clojars.org/repo/") }
        maven { url  = uri("https://jitpack.io") }

        google()

        flatDir {
            dirs("libs")
        }
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}