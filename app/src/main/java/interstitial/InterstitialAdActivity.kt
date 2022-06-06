package interstitial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.cookietech.admoblibrarywithmediation.R
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAdActivity : AppCompatActivity() {
    private var interstitialAd: InterstitialAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interstitial_ad)

        loadInterstitialAd()

    }

    fun loadInterstitialAd()
    {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                interstitialAd = null
            }

            override fun onAdLoaded(interstitialAd_: InterstitialAd) {
                interstitialAd = interstitialAd_
                showInterstitiaAd()
            }
        })
    }

    fun showInterstitiaAd()
    {
        if(interstitialAd == null)
        {
            return
        }
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {

            override fun onAdClicked() {
                Log.d("Rudra_Das"," onAdClicked")


            }

            override fun onAdImpression() {
                Log.d("Rudra_Das"," onAdImpression")
            }

            override fun onAdDismissedFullScreenContent() {
                // Don't forget to set the ad reference to null so you
                // don't show the ad a second time.

                Log.d("Rudra_Das"," onAdDismissedFullScreenContent")
                interstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                Log.d("Rudra_Das"," onAdFailedToShowFullScreenContent")
                interstitialAd = null
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is dismissed.

                Log.d("Rudra_Das"," onAdShowedFullScreenContent")
            }
        }
        interstitialAd?.show(this)
    }


}