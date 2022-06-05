package com.cookietech.admoblibrarywithmediation

import android.app.Application
import android.util.Log
import com.cookietech.admoblibrarywithmediation.Manager.AdLoadListener
import com.cookietech.admoblibrarywithmediation.Manager.AdsManager
import com.cookietech.admoblibrarywithmediation.Manager.Configuration
import com.cookietech.admoblibrarywithmediation.Manager.NativeAdsProvider
import com.cookietech.admoblibrarywithmediation.nativead.NativeAdActivity
import com.google.android.gms.ads.MobileAds

class TestApplication: Application() {

    lateinit var container:Container

    inner class Container{
        lateinit var provider:NativeAdsProvider
        init {
            provider = AdsManager.Builder
                .nativeAdsBuilder(this@TestApplication,"ca-app-pub-3940256099942544/2247696110")
                .addListener(object : AdLoadListener {
                    override fun adLoaded(noOfAds: Int) {
                        Log.d(NativeAdActivity.TAG, "adLoaded: $noOfAds")
                    }

                    override fun adLoadFailed(errorMessage: String) {
                        Log.d(NativeAdActivity.TAG, "adLoaded: $errorMessage")
                    }

                })
//                .configure(Configuration().preload(2))
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