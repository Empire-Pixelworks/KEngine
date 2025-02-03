plugins {
    kotlin("multiplatform") version "2.1.0"
}

group = "com.empirepixelworks"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    js(IR) {
        browser {  }
        binaries.executable()
    }
}

dependencies {
    commonMainImplementation(npm("gl-matrix", "3.4.1"))
}
