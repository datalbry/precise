plugins {
    id("precise.kotlin")
    id("precise.publish-maven-central")
}

dependencies {
    api(project(":api"))

    implementation(libs.bundles.jackson)

    testImplementation(libs.bundles.junit)
}
