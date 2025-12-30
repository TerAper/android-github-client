plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.aper.app_network"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        buildConfig = false
    }
}

dependencies {

    implementation(projects.coreAndroid)
    implementation(projects.coreDomain)

    // ---- Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.okhttp)



    // ---- Retrofit RxJava adapter ----
    implementation(libs.retrofit.rxjava3)

    // ---- RxJava (NO rxandroid here) ----
    implementation(libs.rxjava)

    // ---- Coroutines (for suspend APIs) ----
    implementation(libs.coroutines.core)

    // ---- Hilt ----
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---- Unit testing ----
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

}
