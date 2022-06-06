package com.cookietech.admoblibrarywithmediation.Manager

import com.google.android.gms.ads.interstitial.InterstitialAd

class InterstitialAdsProvider: AdsProvider<InterstitialAd>(Configuration()) {
    override fun <option> loadInternal(callback: callback<option>?, isDestroyed: () -> Boolean) {
        TODO("Not yet implemented")
    }

    override fun preLoad() {
        TODO("Not yet implemented")
    }



}