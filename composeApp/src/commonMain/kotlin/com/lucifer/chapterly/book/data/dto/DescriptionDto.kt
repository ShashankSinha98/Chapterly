package com.lucifer.chapterly.book.data.dto

import kotlinx.serialization.Serializable

/**
 *  Represents the JSON object structure for description field when it's an object.
 * */
@Serializable
data class DescriptionDto(
    val value: String
)
