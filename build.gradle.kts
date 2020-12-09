plugins {
    application
    kotlin("jvm") version "1.4.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "MainKt"
}

repositories {
    mavenCentral()
    flatDir {
        dirs(file("libs"))
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.processing:core:3.3.7")

    implementation(":pdf:3.3.7")
    implementation(":itext:2.1.7")
}
