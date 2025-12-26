plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":core-domain"))

    implementation(libs.javax.inject)

    testImplementation(libs.junit)

}
