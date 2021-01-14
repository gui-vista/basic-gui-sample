package org.example.basic_gui

import gio2.GApplication
import glib2.gpointer
import gtk3.GtkWindowPosition
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.staticCFunction
import org.guiVista.core.fetchEmptyDataPointer
import org.guiVista.gui.GuiApplication

internal lateinit var mainWin: MainWindow

fun main() {
    // Creation and setup of the application takes place in this function.
    GuiApplication(id = "org.example.basicgui").use {
        mainWin = MainWindow(this)
        connectActivateSignal(staticCFunction(::activateApplication), fetchEmptyDataPointer())
        // Run the application, and print out the application status.
        println("Application Status: ${run()}")
    }
}

@Suppress("UNUSED_PARAMETER")
private fun activateApplication(app: CPointer<GApplication>, userData: gpointer) {
    // The default application UI is created in this function.
    mainWin.createUi {
        title = "Basic GUI"
        changePosition(GtkWindowPosition.GTK_WIN_POS_CENTER)
        visible = true
    }
}
