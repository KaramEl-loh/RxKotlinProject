package com.example.myapplication.view

import android.animation.ObjectAnimator
import java.util.*
import io.reactivex.Observable
import kotlin.concurrent.schedule






val Timer.pressAndHoldObservable: Observable<Unit>
    get() = Observable.create { emitter ->
        schedule(1500) {
            emitter.onNext(Unit)
            emitter.onComplete()
        }

        emitter.setCancellable {
            this.cancel()
        }
    }
