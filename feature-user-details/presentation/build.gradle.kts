plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_user_details.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        viewBinding = true
        //compose = true
    }

}

dependencies {
    implementation(projects.coreAndroid)
    implementation(projects.featureUserDetails.domain)
    implementation(projects.featureUserDetails.data)



    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.coil.compose)


    // --- Android UI ---
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.material)

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
