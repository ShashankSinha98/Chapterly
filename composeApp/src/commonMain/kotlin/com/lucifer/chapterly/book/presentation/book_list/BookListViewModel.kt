package com.lucifer.chapterly.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucifer.chapterly.book.domain.Book
import com.lucifer.chapterly.book.domain.BookRepository
import com.lucifer.chapterly.core.domain.onError
import com.lucifer.chapterly.core.domain.onSuccess
import com.lucifer.chapterly.core.presentation.toUiText
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: BookRepository
): ViewModel() {

    private val cachedBooks: List<Book> = emptyList<Book>()
    private var searchJob: Job?= null

    private val _state = MutableStateFlow(BookListState())
    val state = _state
        .onStart {
            if(cachedBooks.isEmpty()) {
                observeSearchQuery()
            }
        }
        .stateIn(
            viewModelScope,
            // Keep the flow active for 5 seconds after the last subscriber disappears
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: BookListAction) {
        when(action) {
            is BookListAction.OnBookClick -> {
                // Handle book click, e.g., navigate to detail screen
            }
            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged() // To filter out consecutive duplicate emissions, Only emits values when they differ from the previous value.
            .debounce(500L) // Only emits a value after a specified time period has passed without any new emissions
            .onEach { query ->
                when {
                    query.isBlank() -> {
                      _state.update {
                          it.copy(
                              errorMessage = null,
                              searchResults = cachedBooks
                          )
                      }
                    }

                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            bookRepository
                .searchBooks(query)
                .onSuccess { searchResults ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            searchResults = searchResults
                        )
                    }
                }.onError { dataError ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = dataError.toUiText()
                        )
                    }
                }
        }
}