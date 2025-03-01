plugins {
    kotlin("multiplatform") version "2.1.0"
    id("maven-publish")
}

group = "com.empirepixelworks"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser {  }
        binaries.library()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(npm("gl-matrix", "3.4.1"))
            }
        }
    }
}
