package com.example.myapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.banner.*


class MainActivity : AppCompatActivity() {

    private var commandButtons: List<LottieButton> = emptyList()

  //  private var banner: Banner = Banner(this, command_buttons)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        commandButtons = listOf(
            LottieButton(this, Commands.LOCK, command_buttons, 0.1f),
            LottieButton(this, Commands.START, command_buttons, 0.15f),
            LottieButton(this, Commands.UNLOCK, command_buttons, 0.1f)
        )

        var banner : Banner = Banner(this,banner_container)
      //  banner = Banner(this, command_buttons)



    }

    override fun onResume() {
        super.onResume()

        Observable.fromIterable(commandButtons)
            .flatMap { it.pressAndHoldObservable }
            .subscribeBy(
                onNext = this::displayLongPressWarningMessage
                //onNext = this::displayWarningBanner
            ).disposedOnPause(this)


    }

    private fun displayWarningBanner(shouldDisplayVehicleStatus: Pair<Boolean, Commands>)
    {





    }


    private fun displayLongPressWarningMessage(shouldSendCommand: Pair<Boolean, Commands>) =
        Toast.makeText(
            this,
            (if (shouldSendCommand.first) "${shouldSendCommand.second.friendlyName} sent to Vehicle" else "Press And Hold"),
            Toast.LENGTH_SHORT
        ).show()
}
