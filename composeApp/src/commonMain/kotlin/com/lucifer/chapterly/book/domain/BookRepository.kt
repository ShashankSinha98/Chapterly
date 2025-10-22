package com.lucifer.chapterly.book.domain

import com.lucifer.chapterly.core.domain.DataError
import com.lucifer.chapterly.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    // General DataError for both Remote and local data sources (description can be cached locally in future)
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>
}