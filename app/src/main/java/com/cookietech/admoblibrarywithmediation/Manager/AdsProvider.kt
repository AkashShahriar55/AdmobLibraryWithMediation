package com.cookietech.admoblibrarywithmediation.Manager

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.ads.nativead.NativeAd
import java.util.*

abstract class AdsProvider<adType>( private val configuration: Configuration) {

    protected val adsStack = Stack<adType>()
    protected abstract fun<option> loadInternal(fetcher: Fetcher<option>)
    protected abstract fun<option> handlePreloadedAds(fetcher: Fetcher<option>)

    protected abstract fun preLoad()

    fun fetch():Fetcher<adType> {
        return Fetcher()
    }

    inner class Fetcher<option>(): LifecycleEventObserver {
        private var isDestroyed = false;
        private var callback: NativeAdsProvider.callback<option>? = null


        fun isDestroyed():Boolean{
            return isDestroyed
        }

        fun getCallback(): NativeAdsProvider.callback<option>? {
            return callback
        }

        fun addCallback(callback: NativeAdsProvider.callback<option>){
            this.callback = callback
            if(configuration.isPreload() && adsStack.size > 0){
                handlePreloadedAds(this)
            }
        }


        fun addObserver(lifecycle: Lifecycle): Fetcher<option> {
            lifecycle.addObserver(this)
            return this
        }



        init {

            if(!configuration.isPreload() || adsStack.size < 1){
                loadInternal(this)
            }else{
                handlePreloadedAds(this)
            }

        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            Log.d(NativeAdsProvider.TAG, "onStateChanged: " + event)

            isDestroyed = event == Lifecycle.Event.ON_DESTROY
        }
    }



}