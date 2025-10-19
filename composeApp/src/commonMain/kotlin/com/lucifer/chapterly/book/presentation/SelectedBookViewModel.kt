package com.lucifer.chapterly.book.presentation

import androidx.lifecycle.ViewModel
import com.lucifer.chapterly.book.domain.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 *  ViewModel to store common data between BookListScreen and BookDetailsScreen.
 *  As Book is a complex data, it is better to store it in a ViewModel rather than passing it through navigation arguments.
 * */
class SelectedBookViewModel: ViewModel() {

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    fun onSelectBook(book: Book?) {
        _selectedBook.value = book
    }
}