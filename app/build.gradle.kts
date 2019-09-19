import java.io.FileInputStream
import java.util.Properties

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

    signingConfigs {
        create("release") {
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
        }
    }

    buildTypes {
        getByName("release") {
            postprocessing {
                isRemoveUnusedCode = true
                isRemoveUnusedResources = true
                isObfuscate = true
                isOptimizeCode = true
                proguardFile("proguard-rules.pro")
            }

            isDebuggable = false

            signingConfig = signingConfigs.getByName("release")
        }

    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    flavorDimensions("default")

    productFlavors {
        create("demo") {
            buildConfigField("String", "BASE_URL", DevelopmentConstants.url)
            versionCode = BuildConfig.versionCode + 10
        }

        create("prod") {
            buildConfigField("String", "BASE_URL", ProductionConstants.url)
            versionCode = BuildConfig.versionCode
        }
        create("instant") {
            versionCode = BuildConfig.versionCode + 1000
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
