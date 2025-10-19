package com.lucifer.chapterly

import androidx.compose.ui.window.ComposeUIViewController
import com.lucifer.chapterly.app.App
import com.lucifer.chapterly.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}