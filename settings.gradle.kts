pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GithubClient"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(
    ":app",
    ":core-domain",
    ":core-android",
    ":feature-login",
    ":feature-profile-repos",
    ":feature-all-users",
    ":feature-user-details",
    ":feature-profile",
    ":feature-settings",
    ":app-database",
    ":app-network"
)
include(":feature-login:data")
include(":feature-login:presentation")
include(":feature-login:domain")
include(":feature-profile:data")
include(":feature-profile:domain")
include(":feature-profile:presentation")
include(":feature-profile-repos:data")
include(":feature-profile-repos:domain")
include(":feature-profile-repos:presentation")
include(":feature-settings:data")
include(":feature-settings:domain")
include(":feature-settings:presentation")
include(":feature-user-details:data")
include(":feature-user-details:domain")
include(":feature-user-details:presentation")
include(":feature-all-users:data")
include(":feature-all-users:domain")
include(":feature-all-users:presentation")
