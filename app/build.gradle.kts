plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.aper.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.aper.app"
        minSdk = 24
        targetSdk = 35
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    // ---- Modules ----
    implementation(project(":core"))
    implementation(project(":app-network"))
    implementation(project(":app-database"))
    implementation(project(":feature-login"))
    implementation(project(":feature-profile"))
    implementation(project(":feature-user-details"))
    implementation(project(":feature-settings"))
    implementation(project(":feature-profile-repos"))
    implementation(project(":feature-all-users"))

    // ---- Android Core ----
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // ---- Splash Screen ----
    implementation(libs.androidx.core.splashscreen)

    // ---- Navigation (XML) ----
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // ---- Lifecycle ----
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.runtime)

    // ---- Hilt ----
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // ---- Test ----
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)

}
