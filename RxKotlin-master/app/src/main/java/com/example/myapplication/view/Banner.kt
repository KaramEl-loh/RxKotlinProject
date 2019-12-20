package com.example.myapplication.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.R


class Banner (context: Context, viewGroup: ViewGroup)
{


    private val banner: View = LayoutInflater.from(context).inflate(R.layout.banner, null)
    private val vehicleStatusTextView = banner.findViewById(R.id.vehicle_status_text) as? TextView
    private val cancelBannerBtn = banner.findViewById(R.id.banner_cancel_button) as? Button



    init {

        vehicleStatusTextView?.text = "Hello"

        viewGroup.addView(banner)

    }







}
















