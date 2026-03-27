package com.fatec.oceane_atv1

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "oceane_atv1",
    ) {
        App()
    }
}