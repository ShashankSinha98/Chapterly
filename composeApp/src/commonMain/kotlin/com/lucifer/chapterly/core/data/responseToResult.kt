package com.lucifer.chapterly.core.data

import com.lucifer.chapterly.core.domain.DataError
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import com.lucifer.chapterly.core.domain.Result

/**
 *  Converts an HttpResponse to a Result type.
 *  Handles different HTTP status codes and maps them to appropriate Result states.
 *  Uses Ktor's body extension function to deserialize the response body to the desired type T.
 *
 *  It catches exceptions that can happen after receiving the response from the server.
 *
 *  @param T The expected type of the response body.
 *  @param response The HttpResponse to be converted.
 *  @return A Result object representing either a successful response with data of type T or an error with a NetworkError.
 * */
suspend inline fun <reified T> responseToResult(
    response: HttpResponse
): Result<T, DataError.Remote> {

    return when(response.status.value) {
        in 200..299 -> { // Success
            try {
                // Ktor's extension function to deserialize the body to T (JSON to data class)
                // requires reified and inline function to know type T at runtime for deserialization
                val data = response.body<T>()
                Result.Success(data)
            } catch (e: NoTransformationFoundException) {
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT)
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS)
        in 500..599 -> Result.Error(DataError.Remote.SERVER_ERROR)
        else -> Result.Error(DataError.Remote.UNKNOWN)
    }
}