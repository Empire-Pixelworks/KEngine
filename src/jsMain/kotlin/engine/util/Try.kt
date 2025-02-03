package engine.util

import engine.errors.EngineError
import engine.errors.KEngineError

sealed class Try<out T> {
    companion object {
        fun <T> tryFrom(f: () -> T): Try<T> {
            return try {
                Success(f())
            } catch (e: Throwable) {
                Failure(KEngineError(e.message ?: e.toString()))
            }
        }
    }
}
data class Success<T>(val value: T): Try<T>()
data class Failure(val error: EngineError): Try<Nothing>()

inline fun <T> Try<T>.onErr(handler: (EngineError) -> Nothing): T = when (this) {
    is Success -> this.value
    is Failure -> handler(error)
}

fun <T> Try<T>.getOrThrow(): T = this.onErr { throw it }
