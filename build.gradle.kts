group = "org.guivista"
version = "0.1-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "1.3.61"
}

repositories {
    mavenLocal()
}

kotlin {
    linuxX64("linux") {
        binaries {
            executable("basic_gui") {
                entryPoint = "org.example.basic_gui.main"
            }
        }
        compilations.getByName("main") {
            dependencies {
                val guiVistaVer = "0.1-SNAPSHOT"
                implementation("org.guivista:guivista-gui-linuxx64:$guiVistaVer")
            }
        }
    }
}
