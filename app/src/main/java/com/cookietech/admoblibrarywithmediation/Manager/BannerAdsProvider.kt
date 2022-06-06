package com.cookietech.admoblibrarywithmediation.Manager

import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd

class BannerAdsProvider:AdsProvider<AdView>(Configuration()) {





    override fun preLoad() {
        TODO("Not yet implemented")
    }

    override fun <option> loadInternal(callback: callback<option>?, isDestroyed: () -> Boolean) {
        TODO("Not yet implemented")
    }

}