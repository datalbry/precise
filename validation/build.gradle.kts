plugins {
    id("precise.kotlin")
    id("precise.publish-maven-central")
}

dependencies {
    val jacksonVersion = "2.12.2"
    val junit5Version = "5.7.1"

    api(project(":api"))

    testImplementation(project(":serialization-jackson"))
    testImplementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
    testImplementation("org.junit.vintage:junit-vintage-engine:$junit5Version")
}
