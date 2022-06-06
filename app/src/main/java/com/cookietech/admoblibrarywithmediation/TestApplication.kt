package com.cookietech.admoblibrarywithmediation

import android.app.Application
import android.util.DisplayMetrics
import android.util.Log
import com.cookietech.admoblibrarywithmediation.Manager.*
import com.google.android.gms.ads.MobileAds


class TestApplication: Application() {

    lateinit var container:Container

    inner class Container{
        lateinit var provider:NativeAdsProvider
        lateinit var interstitialProvider:InterstitialAdsProvider
        lateinit var bannerProvider:BannerAdsProvider
        init {



            provider = AdsManager.Builder.nativeAdsBuilder(this@TestApplication,"ca-app-pub-3940256099942544/2247696110")
                .addListener(object : AdLoadListener{
                    override fun adLoaded(noOfAds: Int) {
                        Log.d("sometag", "adLoaded: " + noOfAds)
                    }

                    override fun adLoadFailed(errorMessage: String) {
                        Log.d("sometag", "adLoadFailed: " + errorMessage)
                    }

                })
                .configure(Configuration().preload(3).setRetryTime(1000))
                .build()


            interstitialProvider = AdsManager.Builder.interstitialAdsBuilder(this@TestApplication,"ca-app-pub-3940256099942544/1033173712")
                .addListener(object : AdLoadListener{
                    override fun adLoaded(noOfAds: Int) {
                        Log.d("sometag", "adLoaded: " + noOfAds)
                    }

                    override fun adLoadFailed(errorMessage: String) {
                        Log.d("sometag", "adLoadFailed: " + errorMessage)
                    }

                })
//                .configure(Configuration().preload(2).setRetryTime(1000))
                .build()


            bannerProvider = AdsManager.Builder.bannerAdsBuilder(this@TestApplication,"ca-app-pub-3940256099942544/6300978111")
                .anchoredBannerAds(300)
                .addListener(object : AdLoadListener{
                    override fun adLoaded(noOfAds: Int) {
                        Log.d("sometag", "adLoaded: " + noOfAds)
                    }

                    override fun adLoadFailed(errorMessage: String) {
                        Log.d("sometag", "adLoadFailed: " + errorMessage)
                    }

                })
//                .configure(Configuration().preload(2).setRetryTime(1000))
                .build()

        }
    }


    override fun onCreate() {
        super.onCreate()

        container = Container()



        MobileAds.initialize(this) {
            Log.d("ads_initialize", "onCreate: " + it.adapterStatusMap)
        }
    }

}