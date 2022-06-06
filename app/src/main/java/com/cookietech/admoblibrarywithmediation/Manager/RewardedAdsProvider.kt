package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class RewardedAdsProvider (private val context: Context,
private val unitId: String,
private val configuration: Configuration,
private val adLoadListener: AdLoadListener?):AdsProvider<RewardedAd>(configuration)  {

    init {


        if(configuration.isPreload()){
            //Log.d(NativeAdsProvider.TAG, "ad preload starting : ")
            preLoad()
        }


    }

    override fun <option> loadInternal(
        getCallback: () -> callback<option>?,
        isDestroyed: () -> Boolean
    ) {
        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(context,unitId,adRequest,
            object : RewardedAdLoadCallback(){
                override fun onAdLoaded(p0: RewardedAd) {

                    if(isDestroyed()){
                        //Log.d(NativeAdsProvider.TAG, "onAdLoaded: activity destroyed")
                        return
                    }

                    getCallback()?.onAdFetched(p0 as option)
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    getCallback()?.onAdFetchFailed(p0.message)
                }

            })
    }

    override fun preLoad() {

        if (adsStack.size < configuration.getNoOfAds()) {

            var adRequest = AdRequest.Builder().build()
            RewardedAd.load(context, unitId, adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adLoadListener?.adLoadFailed("Ad load failed with error : $adError")

                }

                override fun onAdLoaded(rewardedAd: RewardedAd) {

                    adsStack.push(rewardedAd)
                    adLoadListener?.adLoaded(adsStack.size)
                    preLoad()
                }
            })
        }
    }
}