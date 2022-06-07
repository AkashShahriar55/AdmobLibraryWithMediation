package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.rewarded.RewardedAd
import java.util.*
import kotlin.math.min

abstract class AdsProvider<adType> protected constructor(protected val context: Context,protected val unitId:String,protected val configuration: Configuration, private val adLoadListener: AdLoadListener?) {

    protected val adsStack = Stack<adType>()

    private val handler = Handler(Looper.getMainLooper())


    private var retryTime = 1000L;
    private var noOfRetries = 0;


    protected fun<option> loadInternal(onAdFetched:(ads:option)->Unit,onAdFetchFailed:(message:String)->Unit,isDestroyed: ()->Boolean){
        loadAd({ ad ->
            if(isDestroyed())
                return@loadAd
            Log.d(NativeAdsProvider.TAG, "ad loaded on fetch: ")
            onAdFetched(ad as option)
        },{message->
            Log.d(NativeAdsProvider.TAG, "ad load failed on fetch:")
            onAdFetchFailed(message)
        })
    }


    protected fun<option> handlePreLoadedAds(onAdFetched:(ads:option)->Unit,onAdFetchFailed:(message:String)->Unit, isDestroyed: ()->Boolean){
        if(!isDestroyed()){
            if(adsStack.empty()){
                Log.d(NativeAdsProvider.TAG, "ad is empty: ")
                onAdFetchFailed("ad is empty");
            }else{

                onAdFetched(adsStack.pop() as option)
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

                retryTime = configuration.getRetryTime()
                noOfRetries = 0;
            },{ message->
                Log.d(NativeAdsProvider.TAG, "onAdFailedToLoad: $message")

                if(configuration.isExponentialBackOff()){

                    handler.postDelayed({
                        preLoad()
                    },retryTime)

                    retryTime = min(
                        retryTime * 2,
                        Configuration.RETRY_TIMER_MAX_TIME_MILLISECONDS
                    )
                }else{

                    if(noOfRetries < configuration.getMaxNoOfRetries()){
                        handler.postDelayed({
                            preLoad()
                        },retryTime)

                        noOfRetries++
                    }

                }

                adLoadListener?.adLoadFailed(message)

            })
        }
    }


    fun fetch(callback: callback<adType>,listener: AdEventListener? = null):Fetcher<adType> {
        val fullScreenContentCallback = object : FullScreenContentCallback(){
            override fun onAdClicked() {
                listener?.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                listener?.onAdDismissedFullScreenContent()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                listener?.onAdFailedToShowFullScreenContent(p0)
            }

            override fun onAdImpression() {
                listener?.onAdImpression()
            }

            override fun onAdShowedFullScreenContent() {
                listener?.onAdShowedFullScreenContent()
            }
        }
        return Fetcher(callback,fullScreenContentCallback)
    }

    open fun fetch(callback: callback<adType>):Fetcher<adType> {
        return Fetcher(callback)
    }

    inner class Fetcher<option> constructor(private val callback: callback<option>,private val fullScreenContentCallback: FullScreenContentCallback? = null): LifecycleEventObserver {
        private var isDestroyed = false;
        private var currentAd:option? = null



        init {

        }





        fun addObserver(lifecycle: Lifecycle): Fetcher<option> {
            lifecycle.addObserver(this)
            return this
        }



        init {
            if(!configuration.isPreload() || adsStack.size < 1){
                loadInternal<option>({ ad ->
                    currentAd = ad
                    Log.d("callback_test", ": $ad $fullScreenContentCallback")
                    if(ad is InterstitialAd){
                        ad.fullScreenContentCallback = fullScreenContentCallback
                    }else if(ad is RewardedAd){
                        ad.fullScreenContentCallback = fullScreenContentCallback
                    }
                    callback.onAdFetched(ad)
                },{message->
                    callback.onAdFetchFailed(message)
                },{
                    isDestroyed
                })
            }else{

                handlePreLoadedAds<option>({ ad ->
                    currentAd = ad
                    Log.d("callback_test", ": $ad $fullScreenContentCallback")
                    if(ad is InterstitialAd){
                        ad.fullScreenContentCallback = fullScreenContentCallback
                    }else if(ad is RewardedAd){
                        ad.fullScreenContentCallback = fullScreenContentCallback
                    }
                    callback.onAdFetched(ad)
                },{message->
                    callback.onAdFetchFailed(message)
                },{
                    isDestroyed
                })
            }
        }

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            Log.d(NativeAdsProvider.TAG, "onStateChanged: " + event)
            isDestroyed = event == Lifecycle.Event.ON_DESTROY
            if(isDestroyed){
                currentAd?.let {
                    when{
                        currentAd is SimpleNativeAd->{
                           ( currentAd as SimpleNativeAd).getNativeAd().destroy()
                        }

                        currentAd is SimpleBannerAd->{
                            ( currentAd as SimpleBannerAd).getBannerAd().destroy()
                        }
                    }
                }
            }

        }
    }


    interface callback<option>{
        fun onAdFetched(ads:option)
        fun onAdFetchFailed(message:String)
    }





}