object BuildConfig {
    const val buildTools = "28.0.3"
    const val compileSdk = 29
    const val minSdk = 21
    const val targetSdk = 29
    const val versionCode = 1
    const val versionName = "1.0.0"
}

object Versions {
    const val gradle = "3.5.0"
    const val kotlin = "1.3.50"
    const val appcompat = "1.0.2"

    const val junit = "4.12"
}

object Libs {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val retrofit = Retrofit()

    class Retrofit(version: String = "2.5.0") {
        val base = "com.squareup.retrofit2:retrofit:${version}"
        val adapter = "com.squareup.retrofit2:adapter-rxjava2:${version}"
        val gson = "com.squareup.retrofit2:converter-gson:${version}"
    }

}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
}