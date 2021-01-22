plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.1.0"
    `java-library`
    `maven-publish`
    signing
}

group = "me.kingtux"
version = "1.0-SNAPSHOT"
if (hasProperty("buildNumber")) {
    version = "1.0-" + properties.get("buildNumber") + "-SNAPSHOT";
}
val artifactName = "javalinvc"

java {
    withJavadocJar()
    withSourcesJar()
    targetCompatibility = org.gradle.api.JavaVersion.VERSION_11
    sourceCompatibility = org.gradle.api.JavaVersion.VERSION_11
}
repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.onarandombox.com/content/groups/public")
    maven("https://repo.potatocorp.dev/storages/maven/kingtux-repo")
    maven("https://repo.codemc.org/repository/maven-public")
    maven("https://jitpack.io")

}

dependencies {
    implementation(group = "org.apache.commons", name = "commons-text", version = "1.9")
    implementation(group = "io.javalin", name = "javalin", version = "3.9.0")
    implementation(group = "org.webjars", name = "webjars-locator-core", version = "0.45")
    implementation(group = "io.pebbletemplates", name = "pebble", version = "3.1.4")
    implementation(group = "me.kingtux", name = "simpleannotation", version = "1.3.1")
    implementation(group = "me.kingtux", name = "tuxjsitemap", version = "1.1")
}
tasks {
    "shadowJar"(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
        dependencies {
            relocate("org.bstats.bukkit", "me.kingtux.secondend.bstats")
        }
    }
}
publishing {
    publications {
        create<MavenPublication>("mavenJava") {

            artifactId = artifactName
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
                name.set(artifactName)
            }
        }
    }
    repositories {
        maven {

            val releasesRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            val snapshotsRepoUrl = uri("https://repo.kingtux.me/storages/maven/kingtux-repo")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials(PasswordCredentials::class)
        }
        mavenLocal()
    }
}


tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}

tasks.processResources {
    expand("version" to project.version)
}