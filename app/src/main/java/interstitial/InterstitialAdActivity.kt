package interstitial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import com.cookietech.admoblibrarywithmediation.R
import com.cookietech.admoblibrarywithmediation.TestApplication
import com.cookietech.android_ads_library.Manager.AdEventListener
import com.cookietech.android_ads_library.Manager.AdsProvider
import com.cookietech.android_ads_library.Manager.NativeAdsProvider
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.interstitial.InterstitialAd

class InterstitialAdActivity : AppCompatActivity() {
    private var interstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_ad)

        loadInterstitialAd()

    }

    fun loadInterstitialAd()
    {
//        var adRequest = AdRequest.Builder().build()
//
//        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//                interstitialAd = null
//            }
//
//            override fun onAdLoaded(interstitialAd_: InterstitialAd) {
//                interstitialAd = interstitialAd_
//                showInterstitiaAd()
//            }
//        })

        (application as TestApplication).container.interstitialProvider


        (application as TestApplication).container.interstitialProvider
            .fetch(object: AdsProvider.callback<InterstitialAd>{
                override fun onAdFetched(ads: InterstitialAd) {
                    showInterstitiaAd(ads)
                }

                override fun onAdFetchFailed(message: String) {
                    Log.d(NativeAdsProvider.TAG, "onAdFetchFailed: " + message)
                }

            },object : AdEventListener() {
                override fun onAdClicked() {
                    Log.d("callback_test", "onAdClicked: " )
                }

                override fun onAdDismissedFullScreenContent() {
                    Log.d("callback_test", "onAdDismissedFullScreenContent: ")
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    Log.d("callback_test", "onAdFailedToShowFullScreenContent: $p0")
                }

                override fun onAdImpression() {
                    Log.d("callback_test", "onAdImpression: ")
                }

                override fun onAdShowedFullScreenContent() {
                    Log.d("callback_test", "onAdShowedFullScreenContent: ")
                }
            })
            .addObserver(lifecycle)

    }

    fun showInterstitiaAd(interstitialAd: InterstitialAd)
    {
        if(interstitialAd == null)
        {
            return
        }
//        interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
//
//            override fun onAdClicked() {
//                Log.d("Rudra_Das"," onAdClicked")
//
//
//            }
//
//            override fun onAdImpression() {
//                Log.d("Rudra_Das"," onAdImpression")
//            }
//
//            override fun onAdDismissedFullScreenContent() {
//                // Don't forget to set the ad reference to null so you
//                // don't show the ad a second time.
//
//                Log.d("Rudra_Das"," onAdDismissedFullScreenContent")
//            }
//
//            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
//                Log.d("Rudra_Das"," onAdFailedToShowFullScreenContent")
//
//            }
//
//            override fun onAdShowedFullScreenContent() {
//                // Called when ad is dismissed.
//
//                Log.d("Rudra_Das"," onAdShowedFullScreenContent")
//            }
//        }
        interstitialAd.show(this)
    }


}