package com.example.presentation

import androidx.annotation.MainThread
import androidx.lifecycle.Observer

internal class Event<T>(private val value: T) {

    private var delivered = false

    @MainThread
    fun value() = if (delivered) {
        null
    } else {
        delivered = true
        value
    }
}

internal class NonNullObserver<T>(private val observe: (T) -> Unit) : Observer<T> {

    override fun onChanged(t: T?) {
        t?.let { observe(it) }
                ?: throw WhyWasThisApiNullableException
    }
}

internal class EventObserver<T>(private val observe: (T) -> Unit) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        event?.value()?.let { observe(it) }
    }
}

private object WhyWasThisApiNullableException : NullPointerException("Passed null in LiveData")
