plugins {
    id("precise.kotlin")
    id("precise.publish-maven-central")
}

dependencies {
    api(project(":api"))
    api(project(":core"))

    implementation(libs.bundles.jackson)

    testImplementation(libs.bundles.junit)
}
