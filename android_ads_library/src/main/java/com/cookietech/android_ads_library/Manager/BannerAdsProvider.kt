package com.cookietech.android_ads_library.Manager

import android.content.Context
import com.google.android.gms.ads.*

class BannerAdsProvider(
    context: Context,
    unitId: String,
    configuration: Configuration,
    adLoadListener: AdLoadListener?,
    private val adSize: AdSize
): AdsProvider<SimpleBannerAd>(context,unitId,configuration,adLoadListener) {


    init {
        if(configuration.isPreload()){
            preLoad()
        }
    }

    override fun loadAd(
        adLoadSuccess: (ad: SimpleBannerAd) -> Unit,
        adLoadFailed: (message: String) -> Unit
    ) {
        val adView = AdView(context)

        adView.setAdSize(adSize)
        adView.adUnitId = unitId
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                val bannerAd = SimpleBannerAd(context, adView)
                adLoadSuccess(bannerAd)


            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                adLoadFailed(adError.message)
            }
        }
    }

}