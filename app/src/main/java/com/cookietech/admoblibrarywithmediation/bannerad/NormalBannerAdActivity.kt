package com.cookietech.admoblibrarywithmediation.bannerad

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.cookietech.admoblibrarywithmediation.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

class NormalBannerAdActivity : AppCompatActivity() {
    lateinit var adView: AdView
    lateinit var adContainer: FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_banner_ad)

        adView = findViewById(R.id.adview)
        adContainer = findViewById(R.id.ad_container)

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        var adView = AdView(this)
        adView.adUnitId = "ca-app-pub-3940256099942544/9214589741"
        val adSize = AdSize(300, 100)
        adView.setAdSize(adSize)
        adContainer.addView(adView)


        val adRequest2 = AdRequest.Builder().build()
        adView.loadAd(adRequest2)

    }
}