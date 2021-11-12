plugins {
    id("datalbry.kotlin")
    id("datalbry.publication")
}

dependencies {
    api(project(":api"))
    api(project(":core"))

    implementation(libs.bundles.jackson)

    testImplementation(libs.bundles.junit)
}
