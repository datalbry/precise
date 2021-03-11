plugins {
    kotlin("jvm") version "1.4.31"
}

repositories {
    google()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    api(project(":api"))
    implementation(kotlin("stdlib"))

    implementation("com.google.devtools.ksp:symbol-processing-api:1.4.30-1.0.0-alpha02")

    testImplementation("commons-io:commons-io:2.6")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.3.6")
    testImplementation("org.junit.platform:junit-platform-launcher:1.7.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.1")
    testImplementation("org.junit.vintage:junit-vintage-engine:5.7.1")
}
