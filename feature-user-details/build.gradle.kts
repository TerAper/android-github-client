plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.aper.feature_user_details"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}

dependencies {
    implementation(projects.featureUserDetails.presentation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
