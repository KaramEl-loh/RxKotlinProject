package com.example.myapplication.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.myapplication.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


class Banner(context: Context, parentView: ViewGroup) {

    private val banner: View? = LayoutInflater.from(context).inflate(R.layout.banner, parentView)
    private var bannerText = banner?.findViewById(R.id.banner_text) as? TextView
    private var bannerCancelButton = banner?.findViewById(R.id.banner_cancel_button) as? Button
    private var bannerHeight = 200

    init {

        banner?.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, bannerHeight)
        banner?.visibility = View.GONE

    }

    fun showBanner(commandState: Pair<Boolean, Commands>) {

        bannerText?.text =

            (if (commandState.first)
                CommandNotification.commandBannerMessages.getValue(commandState.second).message
            else
                NotificationMessage.PRESS_AND_HOLD.message)

        banner?.visibility = View.VISIBLE

    }

    private val timerObservable: Observable<Long> = Observable.timer(7, TimeUnit.SECONDS)

    private val cancelButtonObservable: Observable<LongPressState>? = bannerCancelButton
        ?.longPressObservable
        ?.filter { it == LongPressState.END }

    val hideBannerObservable: Observable<Any> = Observable.ambArray(timerObservable, cancelButtonObservable)
        .log()
        .observeOn(AndroidSchedulers.mainThread())

    fun hideBanner() {
        banner?.visibility = View.GONE
    }


}


