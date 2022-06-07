package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAdsProvider(
    context: Context,
    unitId: String,
    configuration: Configuration,
    adLoadListener: AdLoadListener?
): AdsProvider<InterstitialAd>(context,unitId,configuration,adLoadListener) {


    init {
        if(configuration.isPreload()){
            preLoad()
        }
    }



    override fun loadAd(
        adLoadSuccess: (ad: InterstitialAd) -> Unit,
        adLoadFailed: (message: String) -> Unit
    ) {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context,unitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adLoadFailed(adError.message)

            }

            override fun onAdLoaded(interstitialAd_: InterstitialAd) {

                adLoadSuccess(interstitialAd_)
            }
        })
    }


}