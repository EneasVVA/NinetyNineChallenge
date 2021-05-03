package com.eneasmacias.challange.core.interactor

import io.reactivex.observers.DisposableSingleObserver

/**
 * Default [DisposableObserver] base class to be used whenever you want default error handling.
 */
open class DefaultSingleObserver<T> : DisposableSingleObserver<T>() {
    override fun onSuccess(t: T) {
        // no-op by default.
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
    }
}