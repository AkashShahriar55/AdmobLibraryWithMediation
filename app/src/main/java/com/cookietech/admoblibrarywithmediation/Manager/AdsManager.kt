package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context

class AdsManager private constructor() {

     abstract class Builder<T> protected constructor(protected val context: Context,protected val unitId: String){
         abstract fun build():T
         protected var configuration = Configuration()
         fun configure(configuration: Configuration):Builder<T>{
             this.configuration = configuration
             return this
         }

         protected var adLoadListener: AdLoadListener? = null

         fun addListener(adLoadListener: AdLoadListener): Builder<T> {
             this.adLoadListener = adLoadListener
             return this
         }


         companion object{
             fun nativeAdsBuilder(context: Context,unitId:String):NativeAdsBuilder{
                 return NativeAdsBuilder(context,unitId);
             }

             fun bannerAdsBuilder(context: Context,unitId: String):BannerAdsBuilder{
                 return BannerAdsBuilder(context,unitId)
             }

             fun interstitialAdsBuilder(context: Context,unitId: String):InterstitialAdsBuilder{
                 return InterstitialAdsBuilder(context,unitId)
             }
             fun rewardedAdsBuilder(context: Context,unitId: String):RewardedAdsBuilder{
                 return RewardedAdsBuilder(context,unitId)
             }
         }

         class NativeAdsBuilder(context: Context,unitId: String): Builder<NativeAdsProvider>(context,unitId) {

             override fun build(): NativeAdsProvider {

                 return NativeAdsProvider(context, unitId,configuration,adLoadListener);
             }

         }

         class BannerAdsBuilder(context: Context,unitId: String):Builder<BannerAdsProvider>(context,unitId){
             override fun build(): BannerAdsProvider {
                 TODO("Not yet implemented")
             }


         }


         class InterstitialAdsBuilder(context: Context,unitId: String):Builder<InterstitialAdsProvider>(context, unitId){

             override fun build(): InterstitialAdsProvider {
                 return InterstitialAdsProvider(context, unitId, configuration, adLoadListener)
             }

         }

         class RewardedAdsBuilder(context: Context,unitId: String):Builder<RewardedAdsProvider>(context, unitId){

             override fun build(): RewardedAdsProvider {
                 return RewardedAdsProvider(context, unitId, configuration, adLoadListener)
             }

         }


     }




    fun createSomething(): AdsManager {
        return this
    }













}