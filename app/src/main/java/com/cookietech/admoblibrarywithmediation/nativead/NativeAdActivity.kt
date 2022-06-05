package com.cookietech.admoblibrarywithmediation.nativead

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cookietech.admoblibrarywithmediation.Manager.AdLoadListener
import com.cookietech.admoblibrarywithmediation.Manager.AdsManager
import com.cookietech.admoblibrarywithmediation.Manager.Configuration
import com.cookietech.admoblibrarywithmediation.Manager.NativeAdsProvider
import com.cookietech.admoblibrarywithmediation.R
import com.cookietech.admoblibrarywithmediation.TestApplication
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import javax.security.auth.callback.Callback

class NativeAdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_ad)






        (application as TestApplication).container.provider.fetch().addObserver(lifecycle).addCallback(object : NativeAdsProvider.callback<NativeAd>{
            override fun onAdFetched(ads: NativeAd) {
                Log.d(TAG, "onAdFetched: ")
            }

            override fun onAdFetchFailed(message: String) {
                Log.d(TAG, "onAdFetchFailed: ")
            }

        })











    }


    companion object{
        const val TAG = "NativeAdsBuilder"
    }
}