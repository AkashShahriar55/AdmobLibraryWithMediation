package com.cookietech.android_ads_library.Manager

import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAdOptions

class NativeAdsProvider internal constructor(
    context: Context,
    unitId: String,
    configuration: Configuration,
    adLoadListener: AdLoadListener?
):
    AdsProvider<SimpleNativeAd>(context,unitId,configuration,adLoadListener) {

    init {
        if(configuration.isPreload()){
            preLoad()
        }
    }



    var videoOptions = VideoOptions.Builder()
        .setStartMuted(true)
        .build()

    var adOptions = com.google.android.gms.ads.formats.NativeAdOptions.Builder()
        .setVideoOptions(videoOptions)
        .build()

    private var isAdLoading: Boolean = false







    companion object{
        const val  TAG = "NativeAdsBuilder"
    }









    override fun loadAd(
        adLoadSuccess: (ad: SimpleNativeAd) -> Unit,
        adLoadFailed: (message: String) -> Unit
    ) {
        val adLoader = AdLoader.Builder(context,unitId)
            .forNativeAd {
                val simpleNativeAd = SimpleNativeAd(context,it)
                adLoadSuccess(simpleNativeAd)
            }.withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    adLoadFailed(adError.message)
                }
            })
            .withNativeAdOptions( NativeAdOptions.Builder().build()).build()

        adLoader.loadAd(AdRequest.Builder().build() )
    }


}