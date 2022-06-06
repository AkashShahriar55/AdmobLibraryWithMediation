package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.google.android.gms.ads.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import java.util.*

class NativeAdsProvider internal constructor(
    private val context: Context,
    private val unitId: String,
    private val configuration: Configuration,
    private val adLoadListener: AdLoadListener?
):
    AdsProvider<NativeAd>(configuration) {

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



    override fun <option> loadInternal(callback: callback<option>?, isDestroyed: () -> Boolean) {
        val adLoader = AdLoader.Builder(context,unitId)
            .forNativeAd {
                if(isDestroyed()){
                    it.destroy();
                    return@forNativeAd
                }

                Log.d(TAG, "ad loaded on fetch: ${callback}")

                if(callback!!.javaClass.isAssignableFrom(NativeAd::class.java)){
                    Log.d(TAG, "loadInternal: class ok")
                }else{
                    Log.d(TAG, "loadInternal: class not ok")
                }




                callback?.onAdFetched(it as option)
            }.withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(TAG, "ad load failed on fetch: ${callback}")
                    callback?.onAdFetchFailed(adError.message)
                }
            })
            .withNativeAdOptions( NativeAdOptions.Builder().build()).build()

        adLoader.loadAd(AdRequest.Builder().build() )
    }

    override fun preLoad(){
        if(adsStack.size < configuration.getNoOfAds()){
            isAdLoading = true
            val adLoader = AdLoader.Builder(context,unitId)
                .forNativeAd {
                    isAdLoading = false
                    adsStack.add(it)

                    adLoadListener?.adLoaded(adsStack.size)


                    preLoad()
                    Log.d(TAG, "loadAd: ad loaded stack size : " + adsStack.size)

                }.withAdListener(object : AdListener(){
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        super.onAdFailedToLoad(adError)
                        isAdLoading = false
                        Log.d(TAG, "onAdFailedToLoad: $adError")
                        adLoadListener?.adLoadFailed(adError.message)

                    }
                })
                .withNativeAdOptions( NativeAdOptions.Builder().build()).build()

            adLoader.loadAd(AdRequest.Builder().build() )
        }
    }


    fun asFragment(): FetchOption<Fragment> {

        return FetchOption<Fragment>()
    }







}