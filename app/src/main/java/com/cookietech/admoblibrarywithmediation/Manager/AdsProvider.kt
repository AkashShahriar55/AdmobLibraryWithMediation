package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import java.util.*

abstract class AdsProvider<adType> protected constructor(protected val context: Context,protected val unitId:String,protected val configuration: Configuration, private val adLoadListener: AdLoadListener?) {

    protected val adsStack = Stack<adType>()


    protected fun<option> loadInternal(getCallback:()->callback<option>?, isDestroyed: ()->Boolean){
        loadAd({ ad ->
            if(isDestroyed())
                return@loadAd
            Log.d(NativeAdsProvider.TAG, "ad loaded on fetch: ${getCallback()}")
            getCallback()?.onAdFetched(ad as option)
        },{message->
            Log.d(NativeAdsProvider.TAG, "ad load failed on fetch: ${getCallback()}")
            getCallback()?.onAdFetchFailed(message)
        })
    }


    protected fun<option> handlePreLoadedAds(getCallback:()->callback<option>?, isDestroyed: ()->Boolean){
        if(!isDestroyed()){
            if(adsStack.empty()){
                Log.d(NativeAdsProvider.TAG, "ad is empty: ")
                getCallback()?.onAdFetchFailed("ad is empty");
            }else{

                getCallback()?.onAdFetched(adsStack.pop() as option)

                preLoad()
            }
        }
    }








    protected abstract fun loadAd(adLoadSuccess:(ad:adType)->Unit,adLoadFailed:(message:String)->Unit)

    protected fun preLoad(){
        if(adsStack.size < configuration.getNoOfAds()){

            loadAd({ ad->
                adsStack.add(ad)
                adLoadListener?.adLoaded(adsStack.size)
                preLoad()
                Log.d(NativeAdsProvider.TAG, "loadAd: ad loaded stack size : " + adsStack.size)
            },{ message->
                Log.d(NativeAdsProvider.TAG, "onAdFailedToLoad: $message")
                adLoadListener?.adLoadFailed(message)
            })
        }
    }


    open fun fetch():Fetcher<adType> {
        return Fetcher()
    }

    inner class Fetcher<option>(): LifecycleEventObserver {
        private var isDestroyed = false;
        private var callback: callback<option>? = null

        init {

        }





        fun addCallback(callback: callback<option>){
            Log.d(NativeAdsProvider.TAG, "addCallback: callback set ")
            this.callback = callback
            if(configuration.isPreload() && adsStack.size > 0){
                handlePreLoadedAds({
                    callback
                },{
                    isDestroyed
                })
            }
        }




        fun addObserver(lifecycle: Lifecycle): Fetcher<option> {
            lifecycle.addObserver(this)
            return this
        }



        init {

            if(!configuration.isPreload() || adsStack.size < 1){
                loadInternal({
                    callback
                },{
                    isDestroyed
                })
            }else{

                handlePreLoadedAds({
                    callback
                },{
                    isDestroyed
                })
            }





        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            Log.d(NativeAdsProvider.TAG, "onStateChanged: " + event)

            isDestroyed = event == Lifecycle.Event.ON_DESTROY
        }
    }


    interface callback<option>{
        fun onAdFetched(ads:option)
        fun onAdFetchFailed(message:String)
    }





}