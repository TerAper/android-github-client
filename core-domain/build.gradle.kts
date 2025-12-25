plugins {
    alias(libs.plugins.kotlin.jvm)
}
kotlin {
    jvmToolchain(17)
}
dependencies {
    implementation(kotlin("stdlib"))

    // Coroutines & Flow (DOMAIN SAFE)
    implementation(libs.kotlinx.coroutines.core)

    // Inject annotations ONLY (no Hilt here)
    implementation(libs.javax.inject)

    testImplementation(libs.junit)
}
