plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.aper.feature_settings.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
