package com.example.myapplication.view

//@file:Suppress("NOTHING_TO_INLINE")


import android.util.Log
import io.reactivex.*

inline fun <reified T> printEvent(tag: String, success: T?, error: Throwable?) =
    when {
        success == null && error == null -> Log.d(tag, "Complete") /* Only with Maybe */
        success != null -> Log.d(tag, "Success $success")
        error != null -> Log.e(tag, "Error $error")
        else -> -1 /* Cannot happen*/
    }

inline fun printEvent(tag: String, error: Throwable?) =
    when {
        error != null -> Log.d(tag, "Error $error")
        else -> Log.d(tag, "Complete")
    }

/**
 * Example usage of [log]:
Single.timer(1, TimeUnit.SECONDS)
.log()
.subscribe({ }, { })
 */

inline fun tag() =
    Thread.currentThread().stackTrace
        .first { it.fileName.endsWith(".kt") }
        .let { stack -> "⚠️ ⚠️ ⚠️ PhotographerRx: " + "${stack.fileName.removeSuffix(".kt")}::${stack.methodName}:${stack.lineNumber}" }

inline fun <reified T> Single<T>.log(tag: String? = null): Single<T> {
    val line = tag ?: tag()
    return doOnEvent { success, error -> printEvent(line, success, error) }
        .doOnSubscribe { Log.d(line, "Subscribe") }
        .doOnDispose { Log.d(line, "Dispose") }
}

inline fun <reified T> Maybe<T>.log(tag: String? = null): Maybe<T> {
    val line = tag ?: tag()
    return doOnEvent { success, error -> printEvent(line, success, error) }
        .doOnSubscribe { Log.d(line, "Subscribe") }
        .doOnDispose { Log.d(line, "Dispose") }
}

inline fun Completable.log(tag: String? = null): Completable {
    val line = tag ?: tag()
    return doOnEvent { printEvent(line, it) }
        .doOnSubscribe { Log.d(line, "Subscribe") }
        .doOnDispose { Log.d(line, "Dispose") }
}

inline fun <reified T> Observable<T>.log(tag: String? = null): Observable<T> {
    val line = tag ?: tag()
    return doOnEach { Log.d(line, "Each $it") }
        .doOnSubscribe { Log.d(line, "Subscribe") }
        .doOnDispose { Log.d(line, "Dispose") }
}

inline fun <reified T> Flowable<T>.log(tag: String? = null): Flowable<T> {
    val line = tag ?: tag()
    return doOnEach { Log.d(line, "Each $it") }
        .doOnSubscribe { Log.d(line, "Subscribe") }
        .doOnCancel { Log.d(line, "Cancel") }
}