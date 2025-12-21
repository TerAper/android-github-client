plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.app_network"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        buildConfig = false
    }
}

dependencies {

    // ---- Core (session data, domain models) ----
    implementation(project(":core"))

    // ---- Retrofit + Moshi ----
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)

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
