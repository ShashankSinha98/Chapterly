package com.lucifer.chapterly.core.domain

typealias DomainError = Error

sealed interface Result<out D, out E: DomainError> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: DomainError>(val error: E): Result<Nothing, E>
}

/**
 * Transforms the successful data of type T to type R using the provided mapping function.
 * If the Result is an Error, it remains unchanged.
 *
 * Used to generally convert DTO models to domain models.
 */
inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Success -> Result.Success(map(data))
        is Result.Error -> Result.Error(error)
    }
}

/**
 * Executes the given [action] if this [Result] is [Result.Success], passing the successful data.
 * Returns the original [Result] unchanged, allowing for fluent chaining.
 *
 * Usage example:
 * result.onSuccess { data -> println("Success: $data") }
 */
inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

/**
 * Executes the given [action] if this [Result] is [Result.Error], passing the error value.
 * Returns the original [Result] unchanged, allowing for fluent chaining.
 *
 * Usage example:
 * result.onError { error -> println("Error: $error") }
 */
inline fun <T, E:Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when(this) {
        is Result.Success -> this
        is Result.Error -> {
            action(error)
            this
        }
    }
}

typealias EmptyResult<E> = Result<Unit, E>