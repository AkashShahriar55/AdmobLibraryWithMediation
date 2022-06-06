package com.cookietech.admoblibrarywithmediation.nativead

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cookietech.admoblibrarywithmediation.Manager.*
import com.cookietech.admoblibrarywithmediation.TestApplication
import com.cookietech.admoblibrarywithmediation.databinding.ActivityNativeAdBinding

class NativeAdActivity : AppCompatActivity() {

    lateinit var binding: ActivityNativeAdBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityNativeAdBinding.inflate(layoutInflater)
        setContentView(binding.root)








        (application as TestApplication).container.provider.fetch().addObserver(lifecycle).addCallback(object :
            AdsProvider.callback<SimpleNativeAd> {
            override fun onAdFetched(ads: SimpleNativeAd) {
                Log.d(TAG, "onAdFetched: ")

                binding.adHolder.addView(ads)

            }

            override fun onAdFetchFailed(message: String) {
                Log.d(TAG, "onAdFetchFailed: ")
            }

        })


//        (application as TestApplication).container.bannerProvider.fetch().addCallback(object : AdsProvider.callback<SimpleBannerAd>{
//            override fun onAdFetched(ads: SimpleBannerAd) {
//
//                binding.adHolder.addView(ads)
//            }
//
//            override fun onAdFetchFailed(message: String) {
//
//            }
//
//        })











    }


    companion object{
        const val TAG = "NativeAdsBuilder"
    }
}