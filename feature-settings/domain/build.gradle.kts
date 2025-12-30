plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.coreDomain)

    testImplementation(libs.junit)
}
