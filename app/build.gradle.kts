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
}

dependencies {

    implementation(projects.appDatabase)
    implementation(projects.appNetwork)
    implementation(projects.coreDomain)
    implementation(projects.coreAndroid)
    implementation(projects.featureLogin)
    implementation(projects.featureProfile)
    implementation(projects.featureUserDetails)
    implementation(projects.featureSettings)
    implementation(projects.featureProfileRepos)
    implementation(projects.featureAllUsers)



    // ---- Android Core ----
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // ---- Splash Screen ----
    implementation(libs.androidx.core.splashscreen)

    // ---- Navigation (XML) ----
    implementation(libs.androidx.navigation.fragment.ktx)
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
