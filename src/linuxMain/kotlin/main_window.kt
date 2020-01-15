package org.example.basic_gui

import glib2.gpointer
import gtk3.GtkButton
import gtk3.GtkOrientation
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.staticCFunction
import org.guiVista.gui.GuiApplication
import org.guiVista.gui.layout.Container
import org.guiVista.gui.layout.boxLayout
import org.guiVista.gui.widget.button.buttonWidget
import org.guiVista.gui.widget.dataEntry.entryWidget
import org.guiVista.gui.widget.display.labelWidget
import org.guiVista.gui.window.AppWindow

internal class MainWindow(app: GuiApplication) : AppWindow(app) {
    // All individual widgets have to be created lazily otherwise a segmentation fault WILL occur. Is is good practise
    // to make all widgets used in a window private.
    private val nameEntry by lazy { createNameEntry() }
    private val greetingLbl by lazy { createGreetingLbl() }
    val stableRef = StableRef.create(this)

    private fun createGreetingLbl() = labelWidget(text = "") {}

    override fun createMainLayout(): Container? = boxLayout(orientation = GtkOrientation.GTK_ORIENTATION_VERTICAL) {
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

    private fun createGreetingBtn() = buttonWidget(label = "Display Greeting") {
        connectClickedSignal(staticCFunction(::greetingBtnClicked), stableRef.asCPointer())
    }

    // Below is an example of controlling/limiting access to some widgets (aka the "Gatekeeper" technique). Note that
    // there is NO direct access to the widgets. This is considered good practise.
    fun updateGreeting() {
        greetingLbl.text = "Hello ${nameEntry.text}! :)"
        nameEntry.text = ""
    }

    override fun resetFocus() {
        // TODO: Workaround Entry.placeholderText bug that occurs when the Entry (nameEntry) has focus.
    }
}

private fun greetingBtnClicked(@Suppress("UNUSED_PARAMETER") btn: CPointer<GtkButton>?, userData: gpointer) {
    val mainWin = userData.asStableRef<MainWindow>().get()
    mainWin.updateGreeting()
}
