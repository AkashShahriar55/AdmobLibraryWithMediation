package com.cookietech.admoblibrarywithmediation

import android.app.Application
import android.util.Log
import com.cookietech.admoblibrarywithmediation.Manager.*
import com.cookietech.admoblibrarywithmediation.nativead.NativeAdActivity
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.nativead.NativeAd

class TestApplication: Application() {

    lateinit var container:Container

    inner class Container{
        lateinit var provider:NativeAdsProvider
        lateinit var interstitialProvider:InterstitialAdsProvider
        lateinit var bannerProvider:BannerAdsProvider
        lateinit var rewardedAdsProvider: RewardedAdsProvider
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
//                .configure(Configuration().preload(3).setRetryTime(1000))
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
                .configure(Configuration().preload(2).setRetryTime(1000))
                .build()

            rewardedAdsProvider = AdsProviderBuilder
                .rewardedAdsBuilder(this@TestApplication,"ca-app-pub-3940256099942544/5224354917")
                .addListener(object: AdLoadListener{
                    override fun adLoaded(noOfAds: Int) {
                        Log.d("sometag", "adLoaded: " + noOfAds)
                    }

                    override fun adLoadFailed(errorMessage: String) {
                        Log.d("sometag", "adLoadFailed: " + errorMessage)
                    }

                })
                .configure(Configuration().preload(2).setRetryTime(1000))
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