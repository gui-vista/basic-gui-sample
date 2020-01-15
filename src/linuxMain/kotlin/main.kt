package org.example.basic_gui

import gio2.GApplication
import glib2.gpointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.staticCFunction
import org.guiVista.core.fetchEmptyDataPointer
import org.guiVista.gui.GuiApplication

private lateinit var mainWin: MainWindow

fun main() {
    // Creation and setup of the application takes place in this function.
    GuiApplication(id = "org.example.basicgui").use {
        mainWin = MainWindow(this)
        connectActivateSignal(staticCFunction(::activateApplication), fetchEmptyDataPointer())
        // Run the application, and print out the application status.
        println("Application Status: ${run()}")
        // Good practise to clean up ALL existing references BEFORE exiting the program.
        mainWin.stableRef.dispose()
    }
}

@Suppress("UNUSED_PARAMETER")
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {
    println("Activating application...")
    // The default application UI is created in this function.
    mainWin.createUi {
        title = "Basic GUI"
        visible = true
    }
}
