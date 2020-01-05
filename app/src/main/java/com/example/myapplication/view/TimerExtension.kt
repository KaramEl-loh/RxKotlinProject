package com.example.myapplication.view

import java.util.*
import io.reactivex.Observable
import kotlin.concurrent.schedule

val Timer.timerObservable: Observable<Unit>
    get() = Observable.create { emitter ->
        schedule(1500) {
            emitter.onNext(Unit)
            emitter.onComplete()
        }

        emitter.setCancellable {
            this.cancel()
        }
    }
