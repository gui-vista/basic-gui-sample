package org.example.basic_gui

import gtk3.GtkButton
import gtk3.gpointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.StableRef
import kotlinx.cinterop.asStableRef
import kotlinx.cinterop.staticCFunction
import org.guivista.core.Application
import org.guivista.core.layout.Box
import org.guivista.core.layout.Container
import org.guivista.core.widget.Button
import org.guivista.core.widget.Label
import org.guivista.core.window.AppWindow

internal class MainWindow(app: Application) : AppWindow(app) {
    private var counter = 0
    private val counterLbl by lazy { createCounterLbl() }
    val stableRef = StableRef.create(this)

    override fun createMainLayout(): Container? = Box().apply {
        val margin = 5
        changeMargins(margin, margin, margin, margin)
        spacing = 5
        prependChild(createIncrementBtn())
        prependChild(counterLbl)
    }

    private fun createCounterLbl() = Label("Counter: 0")

    private fun createIncrementBtn() = Button().apply {
        label = "Increment Counter"
        connectClickedSignal(staticCFunction(::incrementBtnClicked), stableRef.asCPointer())
    }

    fun incrementCounter() {
        counter++
        counterLbl.text = "Counter: $counter"
    }
}

private fun incrementBtnClicked(@Suppress("UNUSED_PARAMETER") btn: CPointer<GtkButton>, userData: gpointer) {
    val mainWin = userData.asStableRef<MainWindow>().get()
    mainWin.incrementCounter()
}
