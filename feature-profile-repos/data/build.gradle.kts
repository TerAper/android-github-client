plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_profile_repos.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}

dependencies {

    // ---------- Project modules ----------
    implementation(project(":core-android"))
    implementation(project(":feature-profile-repos:domain"))
    implementation(project(":app-network"))
    implementation(project(":app-database"))

    // ---------- Hilt ----------
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---------- Retrofit ----------
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)

    // ---------- Moshi ----------
    implementation(libs.moshi.kotlin)

    // ---------- RxJava ----------
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // ---------- Android ----------
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
