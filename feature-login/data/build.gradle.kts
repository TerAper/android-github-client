plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)

}

android {
    namespace = "com.aper.feature_login.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}

dependencies {

    implementation(projects.coreAndroid)
    implementation(projects.coreDomain)
    implementation(projects.featureLogin.domain)
    implementation(projects.appDatabase)
    implementation(projects.appNetwork)


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
