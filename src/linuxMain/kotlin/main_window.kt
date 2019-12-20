package org.example.basic_gui

import gtk3.GtkButton
import gtk3.GtkOrientation
import gtk3.gpointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.staticCFunction
import org.guivista.core.Application
import org.guivista.core.layout.Box
import org.guivista.core.layout.Container
import org.guivista.core.layout.boxLayout
import org.guivista.core.layout.entryWidget
import org.guivista.core.widget.Button
import org.guivista.core.widget.labelWidget
import org.guivista.core.window.AppWindow

internal class MainWindow(app: Application) : AppWindow(app) {
    private val nameEntry by lazy { createNameEntry() }
    private val greetingLbl by lazy { createGreetingLbl() }
    val stableRef = StableRef.create(this)

    private fun createGreetingLbl() = labelWidget("") {}

    override fun createMainLayout(): Container? = Box(orientation = GtkOrientation.GTK_ORIENTATION_VERTICAL).apply {
        spacing = 20
        changeAllMargins(5)
        prependChild(createInputLayout())
        prependChild(greetingLbl)
    }

    private fun createInputLayout() = boxLayout {
        spacing = 5
        prependChild(nameEntry)
        prependChild(createGreetingBtn())
    }

    private fun createNameEntry() = entryWidget {
        text = ""
        placeholderText = "Enter name"
    }

    private fun createGreetingBtn() = Button().apply {
        label = "Display Greeting"
        connectClickedSignal(staticCFunction(::greetingBtnClicked), stableRef.asCPointer())
    }

    fun updateGreeting() {
        greetingLbl.text = "Hello ${nameEntry.text}! :)"
        nameEntry.text = ""
    }

    override fun resetFocus() {
        // TODO: Workaround Entry.placeholderText bug that occurs when the Entry (nameEntry) has focus.
    }
}

private fun greetingBtnClicked(@Suppress("UNUSED_PARAMETER") btn: CPointer<GtkButton>, userData: gpointer) {
    val mainWin = userData.asStableRef<MainWindow>().get()
    mainWin.updateGreeting()
}
