plugins {
    id("datalbry.kotlin")
    id("datalbry.publication")
}

dependencies {
    api(project(":api"))

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.bundles.junit)
}
