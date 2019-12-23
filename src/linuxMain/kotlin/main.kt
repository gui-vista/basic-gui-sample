package org.example.basic_gui

import gtk3.GApplication
import gtk3.gpointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.staticCFunction
import org.guivista.core.Application
import org.guivista.core.fetchEmptyDataPointer

private lateinit var mainWin: MainWindow

fun main() {
    Application("org.example.basicgui").use {
        mainWin = MainWindow(this)
        connectActivateSignal(staticCFunction(::activateApplication), fetchEmptyDataPointer())
        println("Application Status: ${run()}")
        mainWin.stableRef.dispose()
    }
}

@Suppress("UNUSED_PARAMETER")
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {
    println("Activating application...")
    mainWin.createUi {
        title = "Basic GUI"
        visible = true
    }
}
