package org.example.basic_gui

import gtk3.GApplication
import gtk3.gpointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.staticCFunction
import org.guivista.core.Application
import org.guivista.core.window.AppWindow

private lateinit var mainWin: AppWindow

fun main() {
    val data = DummyData()
    Application("org.example.basicgui").use {
        mainWin = AppWindow(this)
        connectActivateSignal(staticCFunction(::activateApplication), data.stableRef.asCPointer())
        connectStartupSignal(staticCFunction(::startupApplication), data.stableRef.asCPointer())
        connectShutdownSignal(staticCFunction(::shutdownApplication), data.stableRef.asCPointer())
        println("Application Status: ${run()}")
        data.stableRef.dispose()
    }
}

@Suppress("UNUSED_PARAMETER")
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {
    println("Activating application...")
    mainWin.createUi {
        title = "Basic GUI"
        changeDefaultSize(800, 600)
        visible = true
    }
}

@Suppress("UNUSED_PARAMETER")
private fun startupApplication(app: CPointer<GApplication>, userData: gpointer) {
    println("Starting application...")
}

@Suppress("UNUSED_PARAMETER")
private fun shutdownApplication(app: CPointer<GApplication>, userData: gpointer) {
    println("Shutting down application...")
}

private class DummyData {
    val stableRef = StableRef.create(this)
}
