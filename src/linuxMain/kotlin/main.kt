package org.example.basic_gui

import glib2.gpointer
import gtk3.GApplication
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.staticCFunction
import org.guiVista.core.fetchEmptyDataPointer
import org.guiVista.gui.Application

private lateinit var mainWin: MainWindow

fun main() {
    Application(id = "org.example.basicgui").use {
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
