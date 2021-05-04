plugins {
    id("precise.kotlin")
    id("precise.publication")
}

dependencies {
    api(project(":api"))
    api(project(":core"))

    implementation(libs.bundles.jackson)

    testImplementation(libs.bundles.junit)
}
