package com.cookietech.admoblibrarywithmediation.Manager

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.lifecycle.ProcessLifecycleOwner
import com.cookietech.admoblibrarywithmediation.TestApplication
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd



class AppOpenAdManager(val activity: FragmentActivity,val adsProvider: AppOpenAdsProvider):Application.ActivityLifecycleCallbacks,
                                                        LifecycleEventObserver{

    var isShowingAd = false
    var adUnitId = "ca-app-pub-3940256099942544/3419835294"
    //var appOpenAd :AppOpenAd? = null
    var currentActivity : Activity? = null
    var cnt = 0

    init {
        activity.application.registerActivityLifecycleCallbacks(this)
        activity.lifecycle.addObserver(this)
    }

    fun fetchAd()
    {
//        if(isAdAvailable())
//        {
//            return
//        }
//
//        Log.d("Rudra_Das","Inside on fetch")
//        var adRequest = AdRequest.Builder().build()
//
//        AppOpenAd.load(context,adUnitId,adRequest,AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT,object : AppOpenAd.AppOpenAdLoadCallback(){
//            override fun onAdLoaded(p0: AppOpenAd) {
//                Log.d("Rudra_Das","Ad Load Succeed.")
//                cnt = 1
//                appOpenAd = p0
//            }
//
//            override fun onAdFailedToLoad(p0: LoadAdError) {
//                Log.d("Rudra_Das","Ad Load Failed.")
//                super.onAdFailedToLoad(p0)
//            }
//
//        })

        adsProvider.fetch(object: AdsProvider.callback<AppOpenAd>{
            override fun onAdFetched(ads: AppOpenAd) {
                showAdIfAvailable(ads)
            }

            override fun onAdFetchFailed(message: String) {
                Log.d("Rudra_Das"," error: " + message)
            }

        })
    }

    fun showAdIfAvailable(appOpenAd: AppOpenAd)
    {
        Log.d("Rudra_Das","inside showAdIfAvailable")
        //Log.d("Rudra_Das"," " + isShowingAd + "  " + isAdAvailable())
        if(!isShowingAd)
        {
            appOpenAd.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdClicked() {
                    super.onAdClicked()
                }
                override fun onAdImpression() {
                    super.onAdImpression()
                }

                override fun onAdShowedFullScreenContent() {

                    isShowingAd = true
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                }

                override fun onAdDismissedFullScreenContent() {
                    //super.onAdDismissedFullScreenContent()
                    isShowingAd = false
                }
            }
            Log.d("Rudra_Das" , "" + currentActivity)
            currentActivity?.let { appOpenAd.show(it) }
        }
    }



    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        currentActivity = p0
    }

    override fun onActivityStarted(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityPaused(p0: Activity) {

    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
        currentActivity = null
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        Log.d("Rudra_Das", "onStateChanged: " + event)
        if(event == Lifecycle.Event.ON_START)
        {
            fetchAd()
        }
    }

//    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//        Log.d("Rudra_Das", "onStateChanged: " + event)
//        if(event == Lifecycle.Event.ON_START)
//        {
//            showAdIfAvailable()
//        }
//
//        //isDestroyed = event == Lifecycle.Event.ON_DESTROY
//    }
}