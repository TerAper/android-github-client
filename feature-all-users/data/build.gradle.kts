plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_all_users.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}

dependencies {

    implementation(project(":core-android"))
    implementation(project(":core-domain"))
    implementation(project(":feature-all-users:domain"))
    implementation(project(":app-network"))
    implementation(project(":app-database"))

    // --- DI ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // --- Networking / DB (if used) ---
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)

    // --- Android basics ---
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
