package com.example.myapplication.view

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import io.reactivex.disposables.Disposable

private class LifecycleDisposable(obj: Disposable) :
    DefaultLifecycleObserver, Disposable by obj {
    override fun onPause(owner: LifecycleOwner) {
        if (!isDisposed) {
            dispose()
        }
    }
}

fun Disposable.disposedOnPause(owner: LifecycleOwner) {
    owner.lifecycle.addObserver(LifecycleDisposable(this))
}
