plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.core_domain.android"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
        buildConfig = false
    }

    composeOptions {
        kotlinCompilerExtensionVersion =
            libs.versions.composeCompiler.get()
    }
}

dependencies {

    implementation(project(":core-domain"))

    // ---- Compose ----
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // ---- Android core ----
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // ---- Hilt ----
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---- Testing ----
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
