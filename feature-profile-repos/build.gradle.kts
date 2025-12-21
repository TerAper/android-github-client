plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_profile_repos"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion =
            libs.versions.composeCompiler.get()
    }

}

dependencies {

    // -------------------- Project modules --------------------
    implementation(project(":core"))
    implementation(project(":app-network"))
    implementation(project(":app-database"))

    // -------------------- Compose BOM --------------------
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // -------------------- Compose UI --------------------
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.material3)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // -------------------- Lifecycle --------------------
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // -------------------- Fragment --------------------
    implementation(libs.androidx.fragment.ktx)

    // -------------------- RxJava --------------------
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.kotlinx.coroutines.rx3)
    implementation(libs.rxjava.kotlin)

    // -------------------- Hilt --------------------
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---- Unit testing ----
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
