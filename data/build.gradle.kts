plugins {
    id(Plugins.ANDROID_LIBRARY)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KAPT)
    id(Plugins.SECRETS_GRADLE_PLUGIN)
    id(Plugins.DAGGER_HILT)
}

android {
    namespace = "com.kenshi.data"
    compileSdk = DefaultConfig.COMPILE_SDK_VERSION

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://dapi.kakao.com\"")
    }
}

dependencies {
    implementation(project(":domain"))

    // Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_MOSHI)

    // Moshi
    implementation(Dependencies.MOSHI)
    kapt(Dependencies.MOSHI_KAPT)

    // Okhttp
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_LOGGING_INTERCEPTOR)

    // Kotlin Serialization
    implementation(Dependencies.KOTLIN_SERIALIZATION)

    // Room
    implementation(Dependencies.ROOM_KTX)
    implementation(Dependencies.ROOM_RUNTIME)
    kapt(Dependencies.ROOM_KAPT)
    implementation(Dependencies.ROOM_PAGING)

    // DataStore
    implementation(Dependencies.PREFERENCES_DATASTORE)

    // Hilt
    implementation(Dependencies.DAGGER_HILT)
    kapt(Dependencies.DAGGER_HILT_KAPT)

    // WorkManager
    implementation(Dependencies.WORKMANGER)
}
