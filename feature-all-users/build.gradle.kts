plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.aper.feature_all_users"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
    }
}

dependencies {

    implementation(projects.featureAllUsers.presentation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso)
}
