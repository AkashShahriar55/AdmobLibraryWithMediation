package com.cookietech.android_ads_library.Manager

import android.content.Context
import android.util.Log
import com.cookietech.android_ads_library.Manager.AdLoadListener
import com.cookietech.android_ads_library.Manager.AdsProvider
import com.cookietech.android_ads_library.Manager.Configuration
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class RewardedAdsProvider (context: Context,
                           unitId: String,
                           configuration: Configuration,
                           adLoadListener: AdLoadListener?):
    AdsProvider<RewardedAd>(context,unitId,configuration,adLoadListener)  {


    init {
        if(configuration.isPreload()){
            preLoad()
        }
    }


    override fun loadAd(
        adLoadSuccess: (ad: RewardedAd) -> Unit,
        adLoadFailed: (message: String) -> Unit
    ) {
        var adRequest = AdRequest.Builder().build()

        RewardedAd.load(context,unitId,adRequest,
            object : RewardedAdLoadCallback(){
                override fun onAdLoaded(ad: RewardedAd) {
                    Log.d("ads_initialize", "onAdLoaded: "+ ad.responseInfo.mediationAdapterClassName)
                   adLoadSuccess(ad)
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    Log.d("ads_initialize", "onAdLoaded: "+ error)
                    adLoadFailed(error.message)
                }

            })
    }
}