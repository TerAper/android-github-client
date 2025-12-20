plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_login"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}


dependencies {

    implementation(project(":core"))
    implementation(project(":app-network"))
    implementation(project(":app-database"))
    implementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(platform(libs.androidx.compose.bom))
    // Android
    implementation(libs.retrofit)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.material)


    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Navigation (Compose)
    implementation(libs.androidx.navigation.compose)

    // RxJava
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---------- Compose ----------
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)

    // Optional Material3 UI
    implementation(libs.androidx.material3)

    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.androidx.ui.tooling)
}
