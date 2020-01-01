package com.example.myapplication.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private var commandButtons: List<LottieButton> = emptyList()
    private lateinit var banner:Banner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        commandButtons = listOf(
            LottieButton(this, Commands.LOCK, command_buttons, 0.1f),
            LottieButton(this, Commands.START, command_buttons, 0.15f),
            LottieButton(this, Commands.UNLOCK, command_buttons, 0.1f)
        )

        banner = Banner(this,banner_container)


    }

    override fun onResume() {
        super.onResume()

        Observable.fromIterable(commandButtons)
            .flatMap { it.pressAndHoldObservable }
            .subscribeBy(
               onNext = this::displayWarningBannerMessage
            ).disposedOnPause(this)
    }


    private fun displayWarningBannerMessage(shouldSendCommand: Pair<Boolean, Commands>)
    {
        banner.showBanner(shouldSendCommand)
        banner.bannerObservable.subscribe {banner.hideBanner()}.disposedOnPause(this)

    }

}
