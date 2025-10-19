package com.lucifer.chapterly

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.lucifer.chapterly.app.App
import com.lucifer.chapterly.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Chapterly",
        ) {
            App()
        }
    }
}