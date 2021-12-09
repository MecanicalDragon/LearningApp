rootProject.name = "gservices"

include(
    "ktor-app",
    "curator-app"
)

pluginManagement {
    repositories {
        maven(url = "https://plugins.gradle.org/m2/")
        gradlePluginPortal()
    }
}
