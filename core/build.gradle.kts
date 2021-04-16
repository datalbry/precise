plugins {
    id("precise.kotlin")
    id("precise.publish-maven-central")
}

dependencies {
    api(project(":api"))

    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    testImplementation(libs.bundles.junit)
}
