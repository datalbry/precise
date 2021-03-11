rootProject.name = "precise"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

include(
    "api",
    "processor-kotlin",
    "validation"
)
