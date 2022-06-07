package com.cookietech.admoblibrarywithmediation

import android.app.Application
import android.util.Log
import com.cookietech.admoblibrarywithmediation.Manager.*
import com.cookietech.admoblibrarywithmediation.nativead.NativeAdActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.nativead.NativeAd
import java.util.*

class TestApplication: Application() {

    lateinit var container:Container
    lateinit var appOpenAdManager: AppOpenAdManager

    inner class Container{
        var provider:NativeAdsProvider
        var interstitialProvider:InterstitialAdsProvider
        var bannerProvider:BannerAdsProvider
        var rewardedAdsProvider: RewardedAdsProvider
        lateinit var appOpenAdsProvider: AppOpenAdsProvider
        init {



            provider = AdsProviderBuilder
                .nativeAdsBuilder(this@TestApplication,"ca-app-pub-3940256099942544/2247696110")
                .addListener(object : AdLoadListener{
                    override fun adLoaded(noOfAds: Int) {
                        Log.d("sometag", "adLoaded: " + noOfAds)
                    }

                    override fun adLoadFailed(errorMessage: String) {
                        Log.d("sometag", "adLoadFailed: " + errorMessage)
                    }

                })
                .configure(Configuration().preload(3))
                .build()


            interstitialProvider = AdsProviderBuilder
                .interstitialAdsBuilder(this@TestApplication,"ca-app-pub-3940256099942544/1033173712")
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

            bannerProvider = AdsProviderBuilder
                .bannerAdsBuilder(this@TestApplication,"ca-app-pub-3940256099942544/6300978111")
                .anchoredBannerAds(300)
                .addListener(object : AdLoadListener{
                    override fun adLoaded(noOfAds: Int) {
                        Log.d("sometag", "adLoaded: " + noOfAds)
                    }

                    override fun adLoadFailed(errorMessage: String) {
                        Log.d("sometag", "adLoadFailed: " + errorMessage)
                    }

                })
                .configure(Configuration().preload(2).linearRetryTime(10000,5))
                .build()

            rewardedAdsProvider = AdsProviderBuilder
                .rewardedAdsBuilder(this@TestApplication,"ca-app-pub-2736964955629655/9133713789")
                .addListener(object: AdLoadListener{
                    override fun adLoaded(noOfAds: Int) {
                        Log.d("sometag", "adLoaded: " + noOfAds)
                    }

                    override fun adLoadFailed(errorMessage: String) {
                        Log.d("sometag", "adLoadFailed: " + errorMessage)
                    }

                })
                .configure(Configuration().preload(2).linearRetryTime(10000,5))
                .build()

            appOpenAdsProvider = AdsProviderBuilder
                .appOpenAdsBuilder(this@TestApplication,"ca-app-pub-3940256099942544/3419835294")
                .addListener(object: AdLoadListener{
                    override fun adLoaded(noOfAds: Int) {
                        Log.d("sometag", "adLoaded: " + noOfAds)
                    }

                    override fun adLoadFailed(errorMessage: String) {
                        Log.d("sometag", "adLoadFailed: " + errorMessage)
                    }

                })
                .configure(Configuration().preload(2).linearRetryTime(10000,5))
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