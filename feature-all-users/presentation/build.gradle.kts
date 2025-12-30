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

    implementation(projects.coreAndroid)
    implementation(projects.coreDomain)
    implementation(projects.featureAllUsers.domain)
    implementation(projects.featureAllUsers.data)
    implementation(projects.featureUserDetails.presentation)



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
