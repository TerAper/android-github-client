plugins {
    alias(libs.plugins.kotlin.jvm)
}
kotlin {
    jvmToolchain(17)
}
dependencies {
    implementation(projects.coreDomain)

    implementation(libs.javax.inject)
    testImplementation(libs.junit)
    implementation(libs.rxjava)
    implementation(libs.kotlinx.coroutines.rx3)

}
