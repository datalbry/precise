import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `java-library`
    idea
    jacoco
}

version = "0.0.1-SNAPSHOT"
group = "io.datalbry.precise"

repositories {
    mavenCentral()
    mavenLocal()
    google()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.21")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

// Do not generate reports for individual projects
tasks.getByName("jacocoTestReport") {
    enabled = false
}

// Prefix all of our jars with the company name
tasks.withType<AbstractArchiveTask> {
    archiveBaseName.set("lbrary-${getArchiveName(this.project)}")
    archiveVersion.set(this.project.version.toString())
}

// We are using Kotlin, so wdk about the Java Version onwards, as we are not relying on Java 11+ features
tasks.withType<JavaCompile> {
    sourceCompatibility = "1.8"
    targetCompatibility = "1.8"
}

// We are using jUnit5 as default
tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}


//Function to calculate unique archive names,
//since we are not prefixing all of our submodules with the parent hierarchy.
fun getArchiveName(project: Project): String {
    var archiveName = project.name
    var parent = project.parent
    while (parent != null) {
        archiveName = "${parent.name}-${archiveName}"
        parent = parent.parent
    }
    return archiveName
}

