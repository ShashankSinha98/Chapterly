package com.lucifer.chapterly.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.lucifer.chapterly.book.presentation.SelectedBookViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

import com.lucifer.chapterly.book.presentation.book_list.BookListScreenRoot
import com.lucifer.chapterly.book.presentation.book_list.BookListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Routes.BookGraph
        ) {
            navigation<Routes.BookGraph>(
                startDestination = BookGraphRoutes.BookList
            ) {
                composable<BookGraphRoutes.BookList> { backStackEntry ->
                    val viewModel = koinViewModel<BookListViewModel>()
                    val selectedBookViewModel = backStackEntry.sharedKoinViewModel<SelectedBookViewModel>(navController)

                    // Clear selected book when entering the list screen
                    LaunchedEffect(true) {
                        selectedBookViewModel.onSelectBook(null)
                    }

                    BookListScreenRoot(
                        viewModel = viewModel,
                        onBookClick = { book ->
                            selectedBookViewModel.onSelectBook(book)
                            navController.navigate(
                                BookGraphRoutes.BookDetail(bookId = book.id)
                            )
                        }
                    )
                }

                composable<BookGraphRoutes.BookDetail> { backStackEntry ->
                    val selectedBookViewModel = backStackEntry.sharedKoinViewModel<SelectedBookViewModel>(navController)
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsState()

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Book Detail Screen! The Book is $selectedBook")
                    }
                }
            }
        }
    }
}

// Extension function to share a ViewModel across a navigation graph
@Composable
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry: NavBackStackEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}