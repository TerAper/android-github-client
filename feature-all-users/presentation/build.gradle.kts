plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_all_users.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation(project(":core-android"))
    implementation(project(":core-domain"))
    implementation(project(":feature-all-users:domain"))
    implementation(project(":feature-all-users:data"))
    implementation(project(":feature-user-details:presentation"))


    implementation(libs.androidx.navigation.fragment.ktx)


    // --- Android UI ---
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.material)

    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.coil.compose)


    // --- Lifecycle ---
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // --- DI ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
