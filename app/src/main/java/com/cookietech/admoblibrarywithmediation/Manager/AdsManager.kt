package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context
import androidx.lifecycle.Lifecycle

class AdsManager private constructor(val adsProvider: AdsProvider) {



     abstract class Builder protected constructor(private val context: Context,private val unitId: String){
         abstract fun build():AdsManager
         companion object{
             fun nativeAdsBuilder(context: Context,unitId:String):NativeAdsBuilder{
                 return NativeAdsBuilder(context,unitId);
             }

             fun bannerAdsBuilder(context: Context,unitId: String):BannerAdsBuilder{
                 return BannerAdsBuilder(context,unitId)
             }
         }
     }

    class NativeAdsBuilder(context: Context,unitId: String): Builder(context,unitId) {

        fun addObserver(lifecycle: Lifecycle){

        }

        override fun build(): AdsManager {
            TODO("Not yet implemented")
        }

    }

    class BannerAdsBuilder(context: Context,unitId: String):Builder(context,unitId){
        override fun build(): AdsManager {
            TODO("Not yet implemented")
        }

    }



    fun load():AdsProvider{
        return adsProvider.load()
    }








}