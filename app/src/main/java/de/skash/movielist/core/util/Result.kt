package de.skash.movielist.core.util

sealed class Result<T> {
    class Success<T>(val value: T) : Result<T>()
    class Loading<T> : Result<T>()
    class Error<T>(val errorType: ErrorType) : Result<T>()

    fun valueOrNull(): T? {
        if (this is Success) {
            return this.value
        }
        return null
    }
}