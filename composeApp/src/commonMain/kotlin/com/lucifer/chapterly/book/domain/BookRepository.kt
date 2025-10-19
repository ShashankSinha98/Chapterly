package com.lucifer.chapterly.book.domain

import com.lucifer.chapterly.core.domain.DataError
import com.lucifer.chapterly.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
}