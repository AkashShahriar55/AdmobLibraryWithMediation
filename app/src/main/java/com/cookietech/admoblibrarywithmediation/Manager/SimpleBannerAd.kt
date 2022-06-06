package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.google.android.gms.ads.AdView

class SimpleBannerAd(context: Context, adView: AdView) : FrameLayout(context) {


    init {

        addView(adView)

    }


}