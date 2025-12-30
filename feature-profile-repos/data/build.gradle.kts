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
    namespace = "com.aper.feature_profile_repos.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}

dependencies {

    implementation(projects.coreAndroid)
    implementation(projects.appNetwork)
    implementation(projects.appDatabase)
    implementation(projects.featureProfileRepos.domain)


    // ---------- Hilt ----------
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---------- Retrofit ----------
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serialization)


    // ---------- RxJava ----------
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // ---------- Android ----------
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
