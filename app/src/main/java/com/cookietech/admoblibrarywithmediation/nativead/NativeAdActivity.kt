package com.cookietech.admoblibrarywithmediation.nativead

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cookietech.admoblibrarywithmediation.Manager.AdsManager
import com.cookietech.admoblibrarywithmediation.R
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAdOptions

class NativeAdActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_native_ad)


        val manager = AdsManager.Builder
            .nativeAdsBuilder(this,"")
            .

        val adLoaderTest = AdLoader.Builder(this,"")

          val adLoader = AdLoader.Builder(this,"ca-app-pub-3940256099942544/2247696110")
            .forNativeAd {
                if(isDestroyed){
                    it.destroy();
                    return@forNativeAd
                }


                Log.d(TAG, "onCreate: ad loaded" )
            }.withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Toast.makeText(this@NativeAdActivity,"Ad not loaded",Toast.LENGTH_SHORT).show()
                }
            })
            .withNativeAdOptions( NativeAdOptions.Builder().build()).build()


        adLoader.loadAd(AdRequest.Builder().build())



    }


    companion object{
        const val TAG = "native_ad"
    }
}