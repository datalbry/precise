plugins {
    id("datalbry.kotlin")
    id("datalbry.publish-internal")
    id("datalbry.publish-maven-central")
    id("io.datalbry.plugin.semver") version "0.2.2"
    idea
}

semver {
    version("alpha", "alpha.{COMMIT_TIMESTAMP}")
    version("beta", "beta.{COMMIT_TIMESTAMP}")
}

group = "io.datalbry.precise"
