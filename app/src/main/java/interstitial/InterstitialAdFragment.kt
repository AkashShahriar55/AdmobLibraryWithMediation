package interstitial

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cookietech.admoblibrarywithmediation.R
import com.cookietech.admoblibrarywithmediation.databinding.FragmentInterstitialAdBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class InterstitialAdFragment : Fragment() {

    private var interstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var binding = FragmentInterstitialAdBinding.inflate(layoutInflater,container,false)
        val view = binding.root
        return  view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadInterstitialAd()

    }

    fun loadInterstitialAd()
    {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(requireActivity(),"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
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
                //super.onAdClicked()
            }

            override fun onAdImpression() {
                Log.d("Rudra_Das"," onAdImpression")
                //super.onAdImpression()
            }

            override fun onAdDismissedFullScreenContent() {
                Log.d("Rudra_Das"," onAdDismissedFullScreenContent")
                //Log.d(TAG, "Ad was dismissed.")
                // Don't forget to set the ad reference to null so you
                // don't show the ad a second time.
                interstitialAd = null
                //loadInterstitialAd()
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                Log.d("Rudra_Das"," onAdFailedToShowFullScreenConten")
                interstitialAd = null
            }

            override fun onAdShowedFullScreenContent() {
                Log.d("Rudra_Das"," onAdShowedFullScreenContent")
                //Log.d(TAG, "Ad showed fullscreen content.")
                // Called when ad is dismissed.
            }
        }
        interstitialAd?.show(requireActivity())
    }

}