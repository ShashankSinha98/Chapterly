package com.lucifer.chapterly.book.data.dto

import kotlinx.serialization.Serializable

/**
 *  @Serializable: For converting Kotlin objects to/from various formats like JSON
 * */
@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
    val description: String? = null
)