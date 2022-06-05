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







    override fun<option> handlePreloadedAds(fetcher: Fetcher<option>) {

        if(fetcher.isDestroyed())
            return

        if(adsStack.empty()){
            Log.d(TAG, "ad is empty: ")
            fetcher.getCallback()?.onAdFetchFailed("ad is empty");
        }else{
            fetcher.getCallback()?.onAdFetched(adsStack.pop() as option)
            preLoad()
        }
    }


    override fun<option> loadInternal(fetcher: Fetcher<option>){
        val adLoader = AdLoader.Builder(context,unitId)
            .forNativeAd {
                if(fetcher.isDestroyed()){
                    it.destroy();
                    return@forNativeAd
                }

                Log.d(TAG, "ad loaded on fetch: ${fetcher.getCallback()}")
                fetcher.getCallback()?.onAdFetched(it as option)
            }.withAdListener(object : AdListener(){
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    super.onAdFailedToLoad(adError)
                    Log.d(TAG, "ad load failed on fetch: ${fetcher.getCallback()}")
                   fetcher.getCallback()?.onAdFetchFailed(adError.message)
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





    interface callback<option>{
        fun onAdFetched(ads:option)
        fun onAdFetchFailed(message:String)
    }

}