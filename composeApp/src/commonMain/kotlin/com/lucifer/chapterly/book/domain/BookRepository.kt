package com.lucifer.chapterly.book.domain

import com.lucifer.chapterly.core.domain.DataError
import com.lucifer.chapterly.core.domain.EmptyResult
import com.lucifer.chapterly.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    // General DataError for both Remote and local data sources (description can be cached locally in future)
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>

    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(bookId: String)
}