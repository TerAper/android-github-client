plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_profile.presentation"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        //viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =
            libs.versions.composeCompiler.get()
    }
}

dependencies {

    implementation(projects.coreAndroid)
    implementation(projects.coreDomain)
    implementation(projects.featureProfile.data)
    implementation(projects.featureProfile.domain)
    implementation(projects.featureSettings.presentation)



    implementation(libs.androidx.navigation.fragment.ktx)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // Lifecycle
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    implementation(libs.coil.compose)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
