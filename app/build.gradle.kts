plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KAPT)
    id(Plugins.HILT_PLUGIN)
}

android {

    defaultConfig {
        applicationId = DefaultConfig.APPLICATION_ID
        minSdk = DefaultConfig.MIN_SDK_VERSION
        targetSdk = DefaultConfig.TARGET_SDK_VERSION
        compileSdk = DefaultConfig.COMPILE_SDK_VERSION
        versionCode = DefaultConfig.VERSION_CODE
        versionName = DefaultConfig.VERSION_NAME

        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Hilt 를 이용한 통합 테스트를 위한 계측 테스트 러너 변경
        testInstrumentationRunner = "com.kenshi.booksearchapp.HiltTestRunner"
    }

    buildTypes {
        named("release") {
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

    // kapt 가 알아서 에러타입을 판단할 수 있도록
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

    implementation(project(":presentation"))
    implementation(project(":data"))
    implementation(project(":domain"))

    // Testing for Local test
    testImplementation(Testing.JUNIT4)
    testImplementation("androidx.test.ext:truth:1.5.0")
    testImplementation("androidx.test:runner:1.5.2")
    testImplementation("org.robolectric:robolectric:4.8.1")
    testImplementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("androidx.test:core:1.5.0")
    // Coroutine Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Testing for 계측 테스트
    androidTestImplementation(Testing.ANDROID_JUNIT)
    androidTestImplementation(Testing.ESPRESSO_CORE)
    // 에스프레소 적용 대상 확장
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.5.1")
    androidTestImplementation("org.hamcrest:hamcrest:2.2")
    androidTestImplementation("androidx.test:core:1.5.0") // Test Core
    androidTestImplementation("androidx.test.ext:truth:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    // Coroutine Test
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    // Hilt For Test
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44.2")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.45")

    // Room
    implementation(Dependencies.ROOM_KTX)
    implementation(Dependencies.ROOM_RUNTIME)
    kapt(Dependencies.ROOM_KAPT)
    implementation(Dependencies.ROOM_PAGING)

    // Hilt
    implementation(Dependencies.DAGGER_HILT)
    kapt(Dependencies.DAGGER_HILT_KAPT)

    // Paging
    implementation(Dependencies.PAGING)

    // Hilt extension
    implementation(Dependencies.HILT_EXTENSION_WORK)
    kapt(Dependencies.HILT_EXTENSION_KAPT)
}

