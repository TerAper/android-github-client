plugins {
    alias(libs.plugins.kotlin.jvm)
}
kotlin {
    jvmToolchain(17)
}
dependencies {
    implementation(projects.coreDomain)

    testImplementation(libs.junit)
}
