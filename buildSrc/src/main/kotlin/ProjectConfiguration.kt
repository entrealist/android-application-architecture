/**
 * Copyright © 2018 Rosberry. All rights reserved.
 *
 * Created by neestell on 20.11.2018.
 */
class ProjectConfiguration(
    val androidBuildConfig: BuildConfig,
    val wearBuildConfig: BuildConfig,
    val buildPlugins: BuildPlugins,

    val constants: Constants,

    val androidLibs: AndroidLibs,
    val wearLibs: WearLibs,
    val tvLibs: TvLibs,

    val androidTestLibs: AndroidTestLibs,
    val wearTestLibs: WearTestLibs
)

class BuildConfig(
    val versionCode: Int,
    val versionName: String,
    val buildToolsVersion: String,
    val minSdkVersion: Int,
    val compileSdkVersion: Int,
    val targetSdkVersion: Int,
    val applicationId: String
)

class BuildPlugins(
    val androidGradle: String,
    val kotlinGradlePlugin: String,
    val dependencyVersionsPlugin: String,
    val fabric: String,
    val playServices: String
)

class Constants(
    val prodBaseUrl: String,
    val developBaseUrl: String,
    val random: String,
    val testRunner: String,
    val youtubeApiKey: String
)

class AndroidLibs(
//UI
    val appCompat: String,
    val recyclerView: String,
    val supportV4: String,
    cardView: String,
    dynamicAnimation: String,
    val constraintLayout: String,
    val materialDesign: String,
    val materialDialogsCore: String,
    getstureViews: String,
    keyboardVisibilityEvent: String,
    textureVideoView: String,
    val playCore: String,

//Play services
    playServicesLocation: String,
    playServicesMaps: String,
    val firebaseCore: String,
    firebaseMessenging: String,
    val firebaseDatabase: String,
    val firebaseAuth: String,
    val firebaseStorage: String,
    val firestore: String,
    firebaseConfig: String,
    firebaseStorageUi: String,
    val firebaseUiAuth: String,

//Tools
    val androidLint: String,
    fabric: String,

//Kotlin
    val kotlinStdLib: String,
    val ankoCommon: String,
    val ankoCoroutines: String,

//API
    val retrofit: String,
    val retrofitAdapterRx: String,
    val retrofitConverterGson: String,
    val okhttpLoggingInteractor: String,
    val okHttp: String,
    val gson: String,

//Rx
    val rxAndroid: String,
    val rxJava: String,
    val rxKotlin: String,
    val rxPermission: String,
    val rxBindingKotlin: String,

//Inject
    val dagger: String,
    daggerAndroid: String,
    daggerAndroidSupport: String,
    val daggerCompiler: String,
    val daggerAndroidProcessor: String,

//Data
    val evernoteAndroidState: String,
    val evernoteAndroidStateProcessor: String,
    roomRuntime: String,
    roomCompiler: String,
    roomRx: String,

//Image
    val glide: String,
    glideCompiler: String,
    val picasso: String,
    picassoOkhttpDownloader: String,
    picassoTransformations: String,
    mediaPicker: String,
    imageCropper: String,
//Sound
    val droidSpeech: String,
//Camera
    val cameraView: String,

//Architecture
    val moxy: String,
    val moxyCompiler: String,
    val moxyAppCompat: String,
    val cicerone: String
)

class AndroidTestLibs(
    jUnit: String,
    espressoCore: String
)

class WearTestLibs()

class WearLibs(
    val supportWearable: String,
    val supportWear: String,
    val wearable: String,
    val playServicesWearable: String
)

class TvLibs(val supportLeanback: String)




