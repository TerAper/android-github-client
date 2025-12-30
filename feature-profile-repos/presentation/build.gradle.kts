plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_profile_repos.presentation"
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
    implementation(projects.featureProfileRepos.domain)
    implementation(projects.featureProfileRepos.data)



    // --- AndroidX ---
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.core.ktx)

    // --- Compose BOM ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)

    // --- Compose Pull-To-Refresh (Material3 experimental) ---
    implementation(libs.androidx.compose.material)


    // --- Lifecycle ---
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // --- Hilt ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // --- RxJava 3 ---
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.rxjava.kotlin)

    // --- Coroutines (needed for StateFlow & viewModelScope) ---
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // --- Tests ---
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
