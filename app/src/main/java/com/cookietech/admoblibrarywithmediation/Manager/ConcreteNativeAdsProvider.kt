package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.cookietech.admoblibrarywithmediation.nativead.NativeAdActivity
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAdOptions

class ConcreteNativeAdsProvider internal constructor(private val context: Context,private val unitId:String):AdsProvider,LifecycleEventObserver {
    var isDestroyed = false;

    init {

        val adLoader = AdLoader.Builder(context,"ca-app-pub-3940256099942544/2247696110")
            .forNativeAd {
                if(isDestroyed){
                    it.destroy();
                    return@forNativeAd
                }


                Log.d(NativeAdActivity.TAG, "onCreate: ad loaded" )
            }.withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                }
            })
            .withNativeAdOptions( NativeAdOptions.Builder().build()).build()
    }




    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d(TAG, "onStateChanged: " + event)

    }


    companion object{
        const val  TAG = "NativeAdsBuilder"
    }

    override fun addObserver(lifecycle: Lifecycle):AdsProvider {
        lifecycle.addObserver(this)
        return this
    }

    override fun load(): AdsProvider {
        return this
    }

}