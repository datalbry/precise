plugins {
    id("precise.kotlin")
    id("precise.publication")
}

dependencies {
    api(project(":api"))

    implementation(libs.bundles.jackson)
    implementation(libs.google.ksp)
    implementation(libs.commons.io)

    testImplementation(libs.testing.ksp)
    testImplementation(libs.bundles.junit)
}
