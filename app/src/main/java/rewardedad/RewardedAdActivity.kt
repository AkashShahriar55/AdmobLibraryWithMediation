package rewardedad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cookietech.admoblibrarywithmediation.R
import com.cookietech.admoblibrarywithmediation.databinding.ActivityRewardedAdBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class RewardedAdActivity : AppCompatActivity() {
    lateinit var binding: ActivityRewardedAdBinding

    private var rewardedAd: RewardedAd? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRewardedAdBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadRewardedAd()

    }

    fun loadRewardedAd()
    {
        var adRequest = AdRequest.Builder().build()
        RewardedAd.load(this,"ca-app-pub-3940256099942544/5224354917",adRequest,
        object : RewardedAdLoadCallback(){
            override fun onAdLoaded(p0: RewardedAd) {
                rewardedAd = p0
                showRewardedAd()
            }

            override fun onAdFailedToLoad(p0: LoadAdError) {
                rewardedAd = null
            }

        })
    }

    fun showRewardedAd()
    {
        if(rewardedAd == null)
        {
            return
        }

        rewardedAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                  // Called when ad fails to show.
                  rewardedAd = null
            }


            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                rewardedAd = null
            }

            override fun onAdClicked() {

            }

            override fun onAdImpression() {

            }
        }

        //rewardedAd.rewardItem.a = 10

        rewardedAd?.show(this, OnUserEarnedRewardListener() {
            fun onUserEarnedReward(rewardItem: RewardItem) {
                var rewardAmount = rewardItem.amount
                var rewardType = rewardItem.type
            }
        })
    }
}