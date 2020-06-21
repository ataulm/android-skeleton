package com.example.presentation

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import java.util.concurrent.TimeUnit

internal fun <T> Flowable<T>.subscribeWith(onNext: Consumer<T>, onError: Consumer<Throwable>): Disposable {
    return subscribe(onNext, onError)
}

internal fun <T> Single<T>.subscribeWith(onSuccess: Consumer<T>, onError: Consumer<Throwable>): Disposable {
    return subscribe(onSuccess, onError)
}

/**
 * Artificial delay is so that we don't flash a loading screen for microseconds
 */
fun <T> Flowable<T>.toRxResultWithInitialDelay(): Flowable<RxResult<T>> {
    return flatMap<RxResult<T>> {
        Flowable.defer { Flowable.just(RxResult.Success(it)) }.delay(1, TimeUnit.SECONDS)
    }.onErrorReturn { RxResult.Error(it) }
            .startWith(RxResult.InFlight)
}

sealed class RxResult<out T> {
    data class Success<out T>(val value: T) : RxResult<T>()
    data class Error(val t: Throwable) : RxResult<Nothing>()
    object InFlight : RxResult<Nothing>()
}
