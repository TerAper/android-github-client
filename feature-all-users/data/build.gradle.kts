plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

configurations.configureEach {
    resolutionStrategy {
        force("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.3")
        force("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    }
}

android {
    namespace = "com.aper.feature_all_users.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

}

dependencies {

    implementation(projects.coreAndroid)
    implementation(projects.coreDomain)
    implementation(projects.featureAllUsers.domain)
    implementation(projects.appNetwork)
    implementation(projects.appDatabase)


    // --- DI ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // --- Networking / DB (if used) ---
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serialization)

    // --- Android basics ---
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
