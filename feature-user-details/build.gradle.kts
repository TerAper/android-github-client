plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_user_details"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // -------------------- Project modules --------------------
    implementation(project(":core"))
    implementation(project(":app-database"))
    implementation(project(":app-network"))

    // -------------------- AndroidX core --------------------
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment.ktx)

    // -------------------- Navigation (XML) --------------------
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // -------------------- Lifecycle --------------------
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // -------------------- RecyclerView --------------------
    implementation(libs.androidx.recyclerview)

    // -------------------- Image loading --------------------
    implementation(libs.coil.core)
    implementation(libs.coil.compose)
    // -------------------- Hilt --------------------
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---- Unit testing ----
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
