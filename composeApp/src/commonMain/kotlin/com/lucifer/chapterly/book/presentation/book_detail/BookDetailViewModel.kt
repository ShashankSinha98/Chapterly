package com.lucifer.chapterly.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger
import com.lucifer.chapterly.app.BookGraphRoutes
import com.lucifer.chapterly.book.domain.BookRepository
import com.lucifer.chapterly.core.domain.onError
import com.lucifer.chapterly.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val l = Logger.withTag("BookDetailViewModel")

    /**
     **`SharingStarted.WhileSubscribed(5000L)`**: Controls when the Flow is active
    - Starts when first subscriber appears
    - Stops 5 seconds after the last subscriber disappears
    - If a new subscriber appears within 5 seconds, it reuses the existing Flow (no r
     */
    private val _state = MutableStateFlow<BookDetailState>(BookDetailState())
    val state = _state
        .onStart { // executes its block when the Flow is first collected
            fetchBookDescription()
        }
        .stateIn( // Convert to StateFlow
            viewModelScope, // Flow lives as long as the ViewModel
            // Keep the flow active for 5 seconds after the last subscriber disappears
            SharingStarted.WhileSubscribed(5000L),
            _state.value // Initial value shown to collectors before any data is fetched
        )

    private val bookId = savedStateHandle.toRoute<BookGraphRoutes.BookDetail>().bookId

    fun onAction(action: BookDetailAction) {
        when (action) {
            BookDetailAction.OnFavoriteClick -> {}
            is BookDetailAction.OnSelectedBookChange -> {
                _state.update {
                    it.copy(book = action.book)
                }
            }

            else -> Unit
        }
    }

    private fun fetchBookDescription() {
        l.d { ":fetchBookDescription" }
        viewModelScope.launch {
            bookRepository
                .getBookDescription(bookId)
                .onSuccess { description ->
                    l.d { "Success: $description" }
                    _state.update {
                        it.copy(
                            book = it.book?.copy(
                                description = description,
                            ),
                            isLoading = false
                        )
                    }
                }
        }
    }
}