package com.cookietech.admoblibrarywithmediation.Manager

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.util.*

abstract class AdsProvider<adType>( private val configuration: Configuration) {

    protected val adsStack = Stack<adType>()
    protected abstract fun<option> loadInternal(getCallback:()->callback<option>?, isDestroyed: ()->Boolean)
    protected abstract fun preLoad()


    init {

    }


    fun fetch():Fetcher<adType> {
        return Fetcher()
    }

    inner class Fetcher<option>(): LifecycleEventObserver {
        private var isDestroyed = false;
        private var callback: callback<option>? = null


        fun isDestroyed():Boolean{
            return isDestroyed
        }

        fun getCallback(): callback<option>? {
            return callback
        }

        fun addCallback(callback: callback<option>){
            Log.d(NativeAdsProvider.TAG, "addCallback: callback set ")
            this.callback = callback
            if(configuration.isPreload() && adsStack.size > 0){
                handlePreloadedAds()
            }
        }

        fun handlePreloadedAds(){
            if(!isDestroyed()){
                if(adsStack.empty()){
                    Log.d(NativeAdsProvider.TAG, "ad is empty: ")
                    callback?.onAdFetchFailed("ad is empty");
                }else{
                    callback?.onAdFetched(adsStack.pop() as option)
                    preLoad()
                }
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
                handlePreloadedAds()
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