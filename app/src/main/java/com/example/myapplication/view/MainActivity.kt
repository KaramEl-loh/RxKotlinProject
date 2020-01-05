package com.example.myapplication.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var commandButtons: List<LottieButton> = emptyList()
    private lateinit var banner: Banner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        commandButtons = listOf(
            LottieButton(this, Commands.LOCK, command_buttons, 0.1f),
            LottieButton(this, Commands.START, command_buttons, 0.15f),
            LottieButton(this, Commands.UNLOCK, command_buttons, 0.1f)
        )
        banner = Banner(this, banner_container)

    }

    override fun onResume() {
        super.onResume()

        Observable.fromIterable(commandButtons)
            .flatMap { it.pressAndHoldObservable }
            .doOnNext(banner::showBanner)
            .switchMap {
                banner.hideBannerObservable
            }
            .subscribeBy(
                onNext = { banner.hideBanner() }

            ).disposedOnPause(this)
    }


}
