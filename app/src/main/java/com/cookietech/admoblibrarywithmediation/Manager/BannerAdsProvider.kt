package com.cookietech.admoblibrarywithmediation.Manager

import androidx.lifecycle.Lifecycle
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd

class BannerAdsProvider:AdsProvider<AdView>(Configuration()) {




    override fun <option> loadInternal(fetcher: Fetcher<option>) {
        TODO("Not yet implemented")



    }

    override fun <option> handlePreloadedAds(fetcher: Fetcher<option>) {
        TODO("Not yet implemented")
    }

    override fun preLoad() {
        TODO("Not yet implemented")
    }

}