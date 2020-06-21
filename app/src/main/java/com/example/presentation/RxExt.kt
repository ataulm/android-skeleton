package com.example.presentation

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

internal fun <T> Flowable<T>.subscribeWith(onNext: Consumer<T>, onError: Consumer<Throwable>): Disposable {
    return subscribe(onNext, onError)
}

internal fun <T> Single<T>.subscribeWith(onSuccess: Consumer<T>, onError: Consumer<Throwable>): Disposable {
    return subscribe(onSuccess, onError)
}
