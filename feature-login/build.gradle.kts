plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.aper.feature_login"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    implementation(projects.featureLogin.presentation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
