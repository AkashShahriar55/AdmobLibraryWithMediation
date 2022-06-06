package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.*

class BannerAdsProvider(
    private val context: Context,
    private val unitId: String,
    configuration: Configuration,
    private val adLoadListener: AdLoadListener?,
    private val adSize: AdSize
):AdsProvider<SimpleBannerAd>(configuration) {






    init {
        if(configuration.isPreload()){
            Log.d(NativeAdsProvider.TAG, "ad preload starting : ")
            preLoad()
        }
    }




    override fun preLoad() {


        if(adsStack.size < configuration.getNoOfAds()){

            val adView = AdView(context)
            val bannerAd = SimpleBannerAd(context,adView)
            adView.setAdSize(adSize)
            adView.adUnitId = unitId
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            adView.adListener = object:AdListener(){
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    adsStack.add(bannerAd)
                    adLoadListener?.adLoaded(adsStack.size)
                    preLoad()
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    adLoadListener?.adLoadFailed(p0.message)
                }
            }
        }

    }

    override fun <option> loadInternal(
        getCallback: () -> callback<option>?,
        isDestroyed: () -> Boolean
    ) {
        if(adsStack.size < configuration.getNoOfAds()){

            val adView = AdView(context)
            val bannerAd = SimpleBannerAd(context,adView)
            adView.setAdSize( adSize)
            adView.adUnitId = unitId
            val adRequest = AdRequest.Builder().build()
            adView.loadAd(adRequest)
            adView.adListener = object:AdListener(){
                override fun onAdLoaded() {
                    super.onAdLoaded()
                    if(isDestroyed()){
                        adView.destroy()
                        return
                    }

                    Log.d(NativeAdsProvider.TAG, "ad loaded on fetch: ${getCallback()}")

//                if(callback!!.javaClass.isAssignableFrom(NativeAd::class.java)){
//                    Log.d(TAG, "loadInternal: class ok")
//                }else{
//                    Log.d(TAG, "loadInternal: class not ok")
//                }

                    getCallback()?.onAdFetched(bannerAd as option)



                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(NativeAdsProvider.TAG, "ad load failed on fetch: ${getCallback()}")
                    getCallback()?.onAdFetchFailed(adError.message)
                }
            }
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