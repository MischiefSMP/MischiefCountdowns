import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

import java.net.URI

plugins {
    kotlin("jvm") version "1.7.20"
    id("MischiefGradleTestServer") version("latest.release")
}

group = "com.mischiefsmp"
version = "0.0.2"

repositories {
    maven {
        url = URI("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        content {
            includeGroup("org.bukkit")
            includeGroup("org.spigotmc")
        }
    }
    maven { url = URI("https://oss.sonatype.org/content/repositories/snapshots") }
    maven { url = URI("https://oss.sonatype.org/content/repositories/central") }
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.14.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

tasks.jar {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from({
        sourceSets.main.get().output
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

tasks.processResources {
    from("src/main/resources") {
        include("plugin.yml")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
        expand("version" to project.version)
    }
}

TestServerConfig {
    version = "1.19.3"
    pluginDir(File("build/libs"))
}