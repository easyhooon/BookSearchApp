plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KAPT)
    id(Plugins.SECRETS_GRADLE_PLUGIN)
    id(Plugins.SAFEARGS)
    id(Plugins.PARCELIZE)
    id(Plugins.HILT_PLUGIN)
}

android {
    compileSdk = DefaultConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.kenshi.booksearchapp"
        minSdk = DefaultConfig.MIN_SDK_VERSION
        targetSdk = DefaultConfig.TARGET_SDK_VERSION
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Hilt 를 이용한 통합 테스트를 위한 계측 테스트 러너 변경
        testInstrumentationRunner = "com.kenshi.booksearchapp.HiltTestRunner"
    }

    buildTypes {
        named("release") {
//            minifyEnabled false
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }

    //kapt 가 알아서 에러타입을 판단할 수 있도록
    kapt {
        correctErrorTypes = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
        animationsDisabled = true
    }
}

dependencies {

    implementation(Dependencies.CORE_KTX)
    implementation(Dependencies.APP_COMPAT)
    implementation(Dependencies.MATERIAL)
    implementation(Dependencies.CONSTRAINT_LAYOUT)


    // Testing for Local test
    testImplementation(Testing.JUNIT4)
    testImplementation("androidx.test.ext:truth:1.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("org.robolectric:robolectric:4.8.1")
    testImplementation("androidx.test.ext:junit-ktx:1.1.3")
    testImplementation("androidx.test:core:1.4.0")
    // Coroutin Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")


    // Testing for 계측 테스트
    androidTestImplementation(Testing.ANDROID_JUNIT)
    androidTestImplementation(Testing.ESPRESSO_CORE)
    // 에스프레소 적용 대상 확장
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.4.0")
    androidTestImplementation("org.hamcrest:hamcrest:2.2")
    androidTestImplementation("androidx.test:core:1.4.0") // Test Core
    androidTestImplementation("androidx.test.ext:truth:1.4.0")
    androidTestImplementation("androidx.test:runner:1.4.0")
    // Coroutin Test
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.2")
    // Hilt For Test
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.42")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.42")

    // Retrofit
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.RETROFIT_CONVERTER_MOSHI)

    // Moshi
    implementation(Dependencies.MOSHI)
    kapt(Dependencies.MOSHI_KAPT)

    // Okhttp
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_LOGGING_INTERCEPTOR)

    // Lifecycle
    implementation(Dependencies.LIFECYCLE_VIEWMODEL_KTX)
    implementation(Dependencies.LIFECYCLE_RUNTIME_KTX)
    implementation(Dependencies.LIFECYCLE_SAVEDSTATE)

    // Coroutine
    implementation(Dependencies.COROUTINE_ANDROID)
    implementation(Dependencies.COROUTINE_CORE)

    // Coil
    implementation(Dependencies.COIL)

    // Recyclerview
    implementation(Dependencies.RECYCLERVIEW)

    // Navigation
    implementation(Dependencies.NAVIGATION_UI_KTX)
    implementation(Dependencies.NAVIGATION_FRAGMENT_KTX)

    // Room
    implementation(Dependencies.ROOM_KTX)
    implementation(Dependencies.ROOM_RUNTIME)
    kapt(Dependencies.ROOM_KAPT)
    implementation(Dependencies.ROOM_PAGING)

    // Kotlin Serialization
    implementation(Dependencies.KOTLIN_SERIALIZATION)

    // DataStore
    implementation(Dependencies.PREFERENCES_DATASTORE)

    // Paging
    implementation(Dependencies.PAGING)

    // WorkManager
    implementation(Dependencies.WORKMANGER)

    // Hilt
    implementation(Dependencies.DAGGER_HILT)
    kapt(Dependencies.DAGGER_HILT_KAPT)

    // ViewModel delegate
    implementation(Dependencies.ACTIVITY_KTX)
    implementation(Dependencies.FRAGMENT_KTX)

    // Hilt extension
    implementation(Dependencies.HILT_EXTENSION_WORK)
    kapt(Dependencies.HILT_EXTENSION_KAPT)
}