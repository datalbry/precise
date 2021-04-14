enableFeaturePreview("VERSION_CATALOGS")
rootProject.name = "precise"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

includeBuild(
    "gradle/convention"
)

include(
    "api",
    "processor-kotlin",
    "validation",
    "serialization-jackson"
)
