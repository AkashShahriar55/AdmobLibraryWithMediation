package com.cookietech.admoblibrarywithmediation

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds

class TestApplication: Application() {


    override fun onCreate() {
        super.onCreate()


        MobileAds.initialize(this) {
            Log.d("ads_initialize", "onCreate: " + it.adapterStatusMap)
        }
    }

}