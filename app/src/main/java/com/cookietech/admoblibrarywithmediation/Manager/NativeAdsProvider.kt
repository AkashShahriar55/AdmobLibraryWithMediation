package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.tasnim.colorsplash.walkthrough.fragments.AdFragment
import java.util.*

class NativeAdsProvider internal constructor(
    private val context: Context,
    private val unitId: String,
    configuration: Configuration,
    private val adLoadListener: AdLoadListener?
):
    AdsProvider<SimpleNativeAd>(configuration) {

    var isFragment = false;

    var videoOptions = VideoOptions.Builder()
        .setStartMuted(true)
        .build()

    var adOptions = com.google.android.gms.ads.formats.NativeAdOptions.Builder()
        .setVideoOptions(videoOptions)
        .build()

    private var isAdLoading: Boolean = false

    init {


        if(configuration.isPreload()){
            Log.d(NativeAdsProvider.TAG, "ad preload starting : ")
            preLoad()
        }


    }






    companion object{
        const val  TAG = "NativeAdsBuilder"
    }


    inner class FetchOption<option>(){

        fun fetch():Fetcher<option>{
            return Fetcher()
        }


    }






    override fun <option> loadInternal(
        getCallback: () -> callback<option>?,
        isDestroyed: () -> Boolean
    ) {
        val adLoader = AdLoader.Builder(context,unitId)
            .forNativeAd {
                if(isDestroyed()){
                    it.destroy();
                    return@forNativeAd
                }

                Log.d(TAG, "ad loaded on fetch: ${getCallback()}")

//                if(callback!!.javaClass.isAssignableFrom(NativeAd::class.java)){
//                    Log.d(TAG, "loadInternal: class ok")
//                }else{
//                    Log.d(TAG, "loadInternal: class not ok")
//                }

                val simpleNativeAd = SimpleNativeAd(context,it)
                getCallback()?.onAdFetched(simpleNativeAd as option)






            }.withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(TAG, "ad load failed on fetch: ${getCallback()}")
                    getCallback()?.onAdFetchFailed(adError.message)
                }
            })
            .withNativeAdOptions( NativeAdOptions.Builder().build()).build()

        adLoader.loadAd(AdRequest.Builder().build() )
    }



    override fun preLoad(){
        if(adsStack.size < configuration.getNoOfAds()){

            val adLoader = AdLoader.Builder(context,unitId)
                .forNativeAd {

                    val simpleNativeAd = SimpleNativeAd(context,it)

                    adsStack.add(simpleNativeAd)

                    adLoadListener?.adLoaded(adsStack.size)


                    preLoad()
                    Log.d(TAG, "loadAd: ad loaded stack size : " + adsStack.size)

                }.withAdListener(object : AdListener(){
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        super.onAdFailedToLoad(adError)

                        Log.d(TAG, "onAdFailedToLoad: $adError")
                        adLoadListener?.adLoadFailed(adError.message)

                    }
                })
                .withNativeAdOptions( NativeAdOptions.Builder().build()).build()

            adLoader.loadAd(AdRequest.Builder().build() )
        }
    }




    override fun fetch(): Fetcher<SimpleNativeAd> {
        isFragment = false
        return super.fetch()
    }

    override fun <option> handlePreLoadedAds(
        getCallback: () -> callback<option>?,
        isDestroyed: () -> Boolean
    ) {
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


}