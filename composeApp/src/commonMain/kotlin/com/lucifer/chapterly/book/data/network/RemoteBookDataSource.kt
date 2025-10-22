package com.lucifer.chapterly.book.data.network

import com.lucifer.chapterly.book.data.dto.BookWorkDto
import com.lucifer.chapterly.book.data.dto.SearchResponseDto
import com.lucifer.chapterly.core.domain.DataError
import com.lucifer.chapterly.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
}