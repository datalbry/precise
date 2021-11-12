plugins {
    id("datalbry.kotlin")
    id("datalbry.publication")
}

dependencies {
    api(project(":api"))

    testImplementation(project(":serialization-jackson"))
    testImplementation(libs.bundles.jackson)
    testImplementation(libs.bundles.junit)
}
