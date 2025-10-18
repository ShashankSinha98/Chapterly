package com.lucifer.chapterly.book.presentation.book_list

import com.lucifer.chapterly.book.domain.Book
import com.lucifer.chapterly.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "",
    val searchResults: List<Book> = sampleBooks, // also acts as all books list
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)

val sampleBook = Book(
    id = "1",
    title = "Sample Book Title",
    imageUrl = "https://edit.org/images/cat/book-covers-big-2019101610.jpg",
    authors = listOf("Author One", "Author Two"),
    description = "This is a sample book description.",
    languages = listOf("en"),
    firstPublishYear = "2020",
    averageRating = 4.5,
    ratingsCount = 150,
    numPages = 320,
    numEditions = 3
)

val sampleBooks = List(10) { i ->
    sampleBook.copy(id = i.toString())
}

