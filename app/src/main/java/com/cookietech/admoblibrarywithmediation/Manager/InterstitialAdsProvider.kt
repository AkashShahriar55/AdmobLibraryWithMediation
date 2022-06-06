package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAdsProvider(
    private val context: Context,
    private val unitId: String,
    configuration: Configuration,
    private val adLoadListener: AdLoadListener?
): AdsProvider<InterstitialAd>(configuration) {



    init {


        if(configuration.isPreload()){
            Log.d(NativeAdsProvider.TAG, "ad preload starting : ")
            preLoad()
        }


    }

    override fun <option> loadInternal(
        getCallback: () -> callback<option>?,
        isDestroyed: () -> Boolean
    ) {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                getCallback()?.onAdFetchFailed(adError.message)

            }

            override fun onAdLoaded(interstitialAd_: InterstitialAd) {

                if(isDestroyed()){
                    Log.d(NativeAdsProvider.TAG, "onAdLoaded: activity destroyed")
                    return
                }

                getCallback()?.onAdFetched(interstitialAd_ as option)
            }
        })
    }

    override fun preLoad() {

        if(adsStack.size < configuration.getNoOfAds()){

            var adRequest = AdRequest.Builder().build()
            InterstitialAd.load(context,unitId, adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adLoadListener?.adLoadFailed("Ad load failed with error : $adError")

                }

                override fun onAdLoaded(interstitialAd_: InterstitialAd) {

                    adsStack.push(interstitialAd_)
                    adLoadListener?.adLoaded(adsStack.size)
                    preLoad()
                }
            })
        }


    }

    override fun <option> handlePreLoadedAds(
        getCallback: () -> callback<option>?,
        isDestroyed: () -> Boolean
    ) {
        if(!isDestroyed()){
            if(adsStack.empty()){
                Log.d(NativeAdsProvider.TAG, "ad is empty: ")
                getCallback()?.onAdFetchFailed("ad is empty");
            }else{
                getCallback()?.onAdFetched(adsStack.pop() as option)
                preLoad()
            }
        }
    }


}