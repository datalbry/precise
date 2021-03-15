plugins {
    kotlin("jvm") version "1.4.31"
    signing
    `maven-publish`
}

repositories {
    google()
}

tasks.withType<Test> {
    useJUnitPlatform()
}

signing {
    sign(configurations.archives.get())
}

group = "io.datalbry.precise"
version = "0.0.1"

signing {
    sign(configurations.archives.get())
}

publishing {
    publications {
        repositories {
            maven {
                name = "MavenCentral"
                url = if (project.rootProject.version.toString().endsWith("SNAPSHOT")) {
                    uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
                } else {
                    uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                }
                credentials {
                    username = project.findProperty("maven.central.username") as String
                    password = project.findProperty("maven.central.password") as String
                }
            }
        }
        create<MavenPublication>("jar") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("Precise - ${project.name}")
                description.set("Precise is a easy to use schema framework")
                url.set("https://github.com/datalbry/precise")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("datalbry")
                        name.set("DataLbry")
                        email.set("precise@datalbry.io")
                    }
                }
                scm {
                    connection.set("https://github.com/datalbry/precise.git")
                    developerConnection.set("scm:git:ssh:git@github.com:datalbry/precise.git")
                    url.set("https://github.com/datalbry/precise")
                }
            }
        }
    }
}

dependencies {
    val jacksonVersion = "2.12.2"
    val junit5Version = "5.7.1"

    api(project(":api"))
    implementation(kotlin("stdlib"))

    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")

    implementation("com.google.devtools.ksp:symbol-processing-api:1.4.30-1.0.0-alpha02")

    testImplementation("commons-io:commons-io:2.6")
    testImplementation("com.github.tschuchortdev:kotlin-compile-testing-ksp:1.3.6")
    testImplementation("org.junit.platform:junit-platform-launcher:1.7.1")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit5Version")
    testImplementation("org.junit.vintage:junit-vintage-engine:$junit5Version")
}
