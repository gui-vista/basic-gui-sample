group = "org.guivista"
version = "0.1-SNAPSHOT"

plugins {
    kotlin("multiplatform") version "1.4.21"
}

repositories {
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/guivista/public")
    }
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
                val guiVistaVer = "0.3.3"
                cinterops.create("glib2")
                cinterops.create("gio2")
                cinterops.create("gtk3")
                implementation("org.guivista:guivista-gui:$guiVistaVer")
            }
        }
    }
}
