package com.lucifer.chapterly.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 *  Represents the individual book model returned from search request of Open Library API
 * */
@Serializable // To convert Json to Kotlin and vice-versa
data class SearchedBookDto(
    @SerialName("key") val id: String,
    @SerialName("title") val title: String,
    @SerialName("language") val languages: List<String>?= null,
    @SerialName("author_key") val authorKeys: List<String>?= null,
    @SerialName("author_name") val authorNames: List<String>?= null,
    @SerialName("cover_edition_key") val coverKey: String?= null,
    @SerialName("cover_i") val coverAlternativeKey: Int?= null,
    @SerialName("first_published_year") val firstPublishYear: Int?= null,
    @SerialName("ratings_average") val ratingsAverage: Double?= null,
    @SerialName("ratings_count") val ratingsCount: Int?= null,
    @SerialName("number_of_pages_median") val numPagesMedian: Int?= null,
    @SerialName("edition_count") val numEditions: Int?= null,
)
