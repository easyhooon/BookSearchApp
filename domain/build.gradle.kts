plugins {
    id(Plugins.JAVA_LIBRARY)
    id(Plugins.KOTLIN_JVM)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    // Paging
    implementation(Dependencies.PAGING_COMMON)

    // Dependency Injection
    implementation(Dependencies.JAVA_INJECT)
}
