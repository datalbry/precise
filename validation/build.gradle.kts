plugins {
    id("precise.kotlin")
    id("precise.publication")
}

dependencies {
    api(project(":api"))

    testImplementation(project(":serialization-jackson"))
    testImplementation(libs.bundles.jackson)
    testImplementation(libs.bundles.junit)
}
