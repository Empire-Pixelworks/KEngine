plugins {
    kotlin("multiplatform") version "2.1.0"
}

group = "com.empirepixelworks"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
}

kotlin {
    js(IR) {
        browser {  }
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation("com.empirepixelworks:KEngine:1.0-SNAPSHOT")
                implementation(npm("gl-matrix", "3.4.1"))
            }
        }
    }
}

//dependencies {
//    commonMainImplementation("com.empirepixelworks:KEngine:1.0-SNAPSHOT")
//    commonMainImplementation(npm("gl-matrix", "3.4.1"))
//}