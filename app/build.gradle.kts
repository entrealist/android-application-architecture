import org.gradle.kotlin.dsl.*
import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

defaultTasks("dependencyUpdates")

val keystoreProperties = Properties().apply {
    load(FileInputStream(rootProject.file("keystore.properties")))
}

android {

    buildToolsVersion(BuildConfig.buildTools)
    compileSdkVersion(BuildConfig.compileSdk)

    defaultConfig {
        minSdkVersion(BuildConfig.minSdk)
        targetSdkVersion(BuildConfig.targetSdk)
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName
        vectorDrawables.useSupportLibrary = true

        testInstrumentationRunnerArgument("disableAnalytics", "true")

        wearAppUnbundled = true
    }

    buildTypes {
        getByName("debug") {

        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    flavorDimensions("default")

    productFlavors {
        create("demo") {

            versionCode = 1
        }

        create("prod") {

            versionCode = 1
        }
        create("instant"){
            versionCode =  1
        }
    }

    sourceSets {
        getByName("main").java.srcDirs("src/main/kotlin")
        getByName("test").java.srcDirs("src/test/kotlin")
    }

    aaptOptions {
        cruncherEnabled = false
    }

    lintOptions {
        isCheckReleaseBuilds = true
        isQuiet = true
        isAbortOnError = false
    }

    compileOptions {
        setSourceCompatibility(JavaVersion.VERSION_1_8)
        setTargetCompatibility(JavaVersion.VERSION_1_8)
    }

    testOptions {
        animationsDisabled = true
        unitTests.apply {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.kotlin)
    implementation(Libs.appcompat)

    implementation(Libs.retrofit.base)
    implementation(Libs.retrofit.adapter)
    implementation(Libs.retrofit.gson)

    testImplementation(TestLibs.junit)
}
