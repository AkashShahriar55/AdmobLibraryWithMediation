package com.cookietech.android_ads_library.Manager

import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd

class AppOpenAdsProvider(
    context: Context,
    unitId: String,
    configuration: Configuration,
    adLoadListener: AdLoadListener?)
    : AdsProvider<AppOpenAd>(context,unitId,configuration,adLoadListener){

    init {
        if(configuration.isPreload()){
            preLoad()
        }
    }

    override fun loadAd(
        adLoadSuccess: (ad: AppOpenAd) -> Unit,
        adLoadFailed: (message: String) -> Unit
    ) {

        var adRequest = AdRequest.Builder().build()

        AppOpenAd.load(context,unitId,adRequest,AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,object : AppOpenAd.AppOpenAdLoadCallback(){
            override fun onAdLoaded(appOpenAd: AppOpenAd) {
                adLoadSuccess(appOpenAd)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
               adLoadFailed(adError.message)
            }

        })

    }

}
