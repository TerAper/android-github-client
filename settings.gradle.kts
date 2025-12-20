pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id("com.android.application") version "8.6.0"
        id("com.android.library") version "8.6.0"
        id("org.jetbrains.kotlin.android") version "1.9.0"
        id("com.google.dagger.hilt.android") version "2.48"
        id("com.google.devtools.ksp") version "1.9.0-1.0.13"
        id("androidx.navigation.safeargs.kotlin") version "2.7.0"
    }
}


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "GithubClient"

include(":app")
include(":core")
include(":feature-login")
include(":feature-profile-repos")
include(":feature-all-users")
include(":feature-user-details")
include(":feature-profile")
include(":feature-settings")
include(":app-database")
include(":app-network")
