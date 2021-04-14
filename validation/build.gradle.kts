plugins {
    id("precise.kotlin")
    id("precise.publish-maven-central")
}

dependencies {
    api(project(":api"))

    testImplementation(project(":serialization-jackson"))
    testImplementation(libs.bundles.jackson)
    testImplementation(libs.bundles.junit)
}
