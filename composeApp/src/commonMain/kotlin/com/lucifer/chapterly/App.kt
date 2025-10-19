package com.lucifer.chapterly

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import chapterly.composeapp.generated.resources.Res
import chapterly.composeapp.generated.resources.compose_multiplatform
import com.lucifer.chapterly.book.data.network.KtorRemoteBookDataSource
import com.lucifer.chapterly.book.data.network.RemoteBookDataSource
import com.lucifer.chapterly.book.data.repository.DefaultBookRepository
import com.lucifer.chapterly.book.domain.BookRepository
import com.lucifer.chapterly.book.presentation.book_list.BookListScreenRoot
import com.lucifer.chapterly.book.presentation.book_list.BookListViewModel
import com.lucifer.chapterly.core.data.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val viewModel = koinViewModel<BookListViewModel>()
    BookListScreenRoot(
        viewModel = viewModel,
        onBookClick = {

        }
    )
}