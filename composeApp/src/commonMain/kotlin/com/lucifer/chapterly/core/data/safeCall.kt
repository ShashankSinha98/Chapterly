package com.lucifer.chapterly.core.data

import com.lucifer.chapterly.core.domain.DataError
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlin.coroutines.coroutineContext
import com.lucifer.chapterly.core.domain.Result

/**
 * A generic function to make safe network calls.
 * It handles exceptions that can happen before the network request is made
 * and converts the HttpResponse to a Result type.
 *
 * @param T The expected type of the successful response data.
 * @param execute A lambda function that performs the network call and returns an HttpResponse.
 * @return A Result containing either the successful data of type T or a NetworkError.
 */
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Remote> {

    val response: HttpResponse = try {
        execute()
    } catch (e: UnresolvedAddressException) { // no internet connection
        return Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: SerializationException) {
        return Result.Error(DataError.Remote.SERIALIZATION)
    } catch (e: Exception) {
        // To prevent catching CancellationException from coroutine cancellation
        // we check if the coroutine was cancelled here
        // If it was cancelled, ensureActive() will throw a CancellationException
        coroutineContext.ensureActive()
        return Result.Error(DataError.Remote.UNKNOWN)
    }

    return responseToResult<T>(response)
}