package com.example.presentation

import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

// allows named args
internal fun <T> Single<T>.subscribeWith(onSuccess: Consumer<T>, onError: Consumer<Throwable>): Disposable {
    return subscribe(onSuccess, onError)
}
