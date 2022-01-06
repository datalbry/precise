plugins {
    id("datalbry.kotlin")
    id("datalbry.publish-internal")
    id("datalbry.publish-maven-central")
    id("io.datalbry.plugin.semver") version "0.2.2"
    id("io.datalbry.catalog.updater") version "0.0.2"
    idea
}

semver {
    version("alpha", "alpha.{COMMIT_TIMESTAMP}")
    version("beta", "beta.{COMMIT_TIMESTAMP}")
}

catalogUpdater {
    from = "./gradle/libs.versions.toml"
    to = "./gradle/libs.versions.toml"
}

group = "io.datalbry.precise"
