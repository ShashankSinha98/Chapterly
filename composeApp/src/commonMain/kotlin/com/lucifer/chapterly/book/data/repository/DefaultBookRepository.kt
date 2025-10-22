package com.lucifer.chapterly.book.data.repository

import com.lucifer.chapterly.book.data.mappers.toBook
import com.lucifer.chapterly.book.data.network.RemoteBookDataSource
import com.lucifer.chapterly.book.domain.Book
import com.lucifer.chapterly.book.domain.BookRepository
import com.lucifer.chapterly.core.domain.DataError
import com.lucifer.chapterly.core.domain.Result
import com.lucifer.chapterly.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
): BookRepository {

    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query)
            .map { dto ->
                dto.results.map { it.toBook() }
            }
    }

    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        return remoteBookDataSource
            .getBookDetails(bookId)
            .map { it.description }
    }
}