package com.cookietech.android_ads_library.Manager

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.google.android.gms.ads.AdView

class SimpleBannerAd(context: Context, private val adView: AdView) : FrameLayout(context) {
    fun getBannerAd(): AdView {
        return adView
    }


    init {

        addView(adView)

    }


}