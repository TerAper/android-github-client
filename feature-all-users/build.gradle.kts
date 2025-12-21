plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.feature_all_users"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // --- Project modules ---
    implementation(project(":core"))
    implementation(project(":app-database"))
    implementation(project(":app-network"))
    implementation(project(":feature-profile-repos"))

    implementation(libs.coil.core)


    // --- Android basics ---
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // --- Fragment + Navigation ---
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // --- Lifecycle (XML-based screen) ---
    implementation(libs.androidx.lifecycle.runtime)

    // --- UI ---
    implementation(libs.androidx.material)
    implementation(libs.androidx.swiperefreshlayout)

    // --- Dependency Injection ---
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---- Unit testing ----
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
