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

include(
    ":app",
    ":core",
    ":feature-login",
    ":feature-profile-repos",
    ":feature-all-users",
    ":feature-user-details",
    ":feature-profile",
    ":feature-settings",
    ":app-database",
    ":app-network"
)
