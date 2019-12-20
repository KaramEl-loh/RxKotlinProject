package com.example.myapplication.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var commandButtons: List<LottieButton> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        commandButtons = listOf(
            LottieButton(this, Commands.LOCK, command_buttons, 0.1f),
            LottieButton(this, Commands.START, command_buttons, 0.15f),
            LottieButton(this, Commands.UNLOCK, command_buttons, 0.1f)
        )
    }

    override fun onResume() {
        super.onResume()

        Observable.fromIterable(commandButtons)
            .flatMap { it.pressAndHoldObservable }
            .subscribeBy(
                onNext = this::displayLongPressWarningMessage
            ).disposedOnPause(this)
    }

    private fun displayLongPressWarningMessage(shouldSendCommand: Pair<Boolean, Commands>) =
        Toast.makeText(
            this,
            (if (shouldSendCommand.first) "${shouldSendCommand.second.friendlyName} sent to Vehicle" else "Press And Hold"),
            Toast.LENGTH_SHORT
        ).show()
}
