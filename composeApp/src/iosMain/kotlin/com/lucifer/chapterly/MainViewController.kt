package com.lucifer.chapterly

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.lucifer.chapterly.di.initKoin
import io.ktor.client.engine.darwin.Darwin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}