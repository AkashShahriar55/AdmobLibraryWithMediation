package com.cookietech.admoblibrarywithmediation.bannerad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.FrameLayout
import com.cookietech.admoblibrarywithmediation.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class AdaptiveBannerAdActivity : AppCompatActivity() {
    lateinit var adContainer:FrameLayout

    private lateinit var adView: AdView
    private var initialLayoutComplete = false

    private val adSize: AdSize
        get() {
            val display = windowManager.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = adContainer.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adaptive_banner_ad)

        adContainer = findViewById(R.id.ad_view_Container)
        adView = AdView(this)
        adContainer.addView(adView)
        adContainer.viewTreeObserver.addOnGlobalLayoutListener {
            if (!initialLayoutComplete) {
                initialLayoutComplete = true
                loadBanner()
            }
        }

    }

    private fun loadBanner() {
        adView.adUnitId = "ca-app-pub-3940256099942544/9214589741"

        adView.setAdSize(adSize)


        var adRequest = AdRequest.Builder().build()

        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }
}