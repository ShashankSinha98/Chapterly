package com.lucifer.chapterly.book.data.network

import com.lucifer.chapterly.book.data.dto.SearchResponseDto
import com.lucifer.chapterly.book.domain.Book
import com.lucifer.chapterly.core.data.safeCall
import com.lucifer.chapterly.core.domain.DataError
import com.lucifer.chapterly.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.parameters

private const val BASE_URL = "https://openlibrary.org"

class KtorRemoteBookDataSource(
    private val httpClient: HttpClient
): RemoteBookDataSource {

    override suspend fun searchBooks(
        query: String,
        resultLimit: Int?
    ): Result<SearchResponseDto, DataError.Remote> {
        return safeCall {
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ) {
                // passing parameters
                parameter("q", query)
                parameter("limit", resultLimit)
                parameter("language", "eng")
                // fields which we want to get response
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
            }
        }
    }
}