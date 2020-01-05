package com.example.myapplication.view

import android.view.MotionEvent.*
import android.view.View
import android.widget.Button
import io.reactivex.Observable

val Button.longPressObservable: Observable<LongPressState>
    get() =
        Observable.create { emitter ->
            View.OnTouchListener { _, event ->

                event?.let {

                    when (it.action) {
                        ACTION_DOWN -> emitter.onNext(LongPressState.BEGAN)
                        ACTION_UP -> emitter.onNext(LongPressState.END)
                        ACTION_CANCEL -> emitter.onNext(LongPressState.CANCEL)
                    }
                }

                true

            }.let {
                setOnTouchListener(it)
            }

            emitter.setCancellable {
                setOnTouchListener(null)
            }
        }