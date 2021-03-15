plugins {
    id("precise.kotlin")
    id("precise.publish-maven-central")
}

dependencies {
    val jacksonVersion = "2.12.2"
    val junit5Version = "5.7.1"

    api(project(":api"))

    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    implementation("com.google.devtools.ksp:symbol-processing-api:1.4.30-1.0.0-alpha02")

    testImplementation("commons-io:commons-io:2.6")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.3.6")
    testImplementation("org.junit.platform:junit-platform-launcher:1.7.1")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
    testImplementation("org.junit.vintage:junit-vintage-engine:$junit5Version")
}
