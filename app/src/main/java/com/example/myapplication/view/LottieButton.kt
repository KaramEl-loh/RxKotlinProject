package com.example.myapplication.view

import android.content.Context
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.marginRight
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*

class LottieButton(
	context: Context,
	val commands: Commands,
	rootView: ViewGroup,
	weight: Float
) {
	private val lottieButton: View =
		LayoutInflater.from(context).inflate(R.layout.lottie_button, null)

	private val button = lottieButton.findViewById(R.id.button) as? Button
	private val progress = lottieButton.findViewById(R.id.progress_bar) as? LottieAnimationView


	init {
		button?.text = commands.friendlyName
		progress?.speed = 0.9f
		(rootView as? LinearLayout)?.addView(lottieButton)
		lottieButton.layoutParams =
			LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, weight)
	}

	private fun resumeAnimation() {
		progress?.visibility = View.VISIBLE
		progress?.playAnimation()
	}

	private fun pauseAnimation() {
		progress?.visibility = View.GONE
		progress?.pauseAnimation()
	}

	val pressAndHoldObservable by lazy {
		button?.longPressObservable?.share()
			?.let { longPress: Observable<LongPressState> ->
				val began: Observable<LongPressState> =
					longPress.filter { it == LongPressState.BEGAN }
				val end: Observable<LongPressState> = longPress.filter { it == LongPressState.END }

				began
					.doOnNext { resumeAnimation() }
					.concatMap {
						val timer = Timer("PressAndHold", false).pressAndHoldObservable
						Observable.ambArray(end.map { false }, timer.map { true })
							.take(1)
							.observeOn(AndroidSchedulers.mainThread())
							.doOnNext { pauseAnimation() }
							.map { Pair(it, commands) }
					}
			}

	}
}
