plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.coreDomain)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.javax.inject)
    testImplementation(libs.junit)

}
