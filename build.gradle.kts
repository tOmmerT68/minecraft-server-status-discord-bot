plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("java")
    kotlin("jvm") version "1.9.21"
}

group = "nl.tommert"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://jitpack.io")
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.20.4-R0.1-SNAPSHOT")
    implementation("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.4")
    implementation("net.dv8tion:JDA:5.0.0-beta.18") {
        exclude(module = "opus-java")
    }
    implementation("net.sf.trove4j:trove4j:3.0.3")
    implementation ("com.fasterxml.jackson.core:jackson-databind:2.14.0-rc1")
}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks.withType<JavaCompile>().configureEach {
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release = targetJavaVersion
    }
}

tasks.named("processResources") {
    val props = mapOf("version" to version)
    inputs.properties(props)
    (this as CopySpec).apply {
        filteringCharset = "UTF-8"
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}

tasks.named("shadowJar") {
    doLast {
        (this as com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar).minimize()
    }
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "StatusPluginKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    from(projectDir) {
        include("plugin.yml")
    }
}
