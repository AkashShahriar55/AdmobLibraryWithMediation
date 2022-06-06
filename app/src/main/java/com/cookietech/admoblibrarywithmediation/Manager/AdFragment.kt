package com.tasnim.colorsplash.walkthrough.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.cookietech.admoblibrarywithmediation.R

import com.google.android.gms.ads.nativead.MediaView

import com.google.android.gms.ads.nativead.NativeAd

import com.google.android.gms.ads.nativead.NativeAdView



class AdFragment(private val nativeAd: NativeAd) : Fragment() {

    private var mLastClickTime: Long = 0
    private var isAdloaded: Boolean = false
    lateinit var adview: NativeAdView



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.ad_fragment, container, false)
        adview = view.findViewById<NativeAdView>(R.id.unifiedNativeAdView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateUnifiedNativeAdView(nativeAd,adview)
        Log.d("native_ads", "onViewCreated: ")
    }

    fun animateAdview(){

        if(isAdloaded){
            Handler().postDelayed({
                adview.visibility = View.VISIBLE
               // adview.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_left));
                adview.startAnimation(inFromRightAnimation())

            }, 1000)
        }
    }

    private fun inFromRightAnimation(): Animation? {
        val inFromRight: Animation = TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, +1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f)
        inFromRight.duration = 1000
        inFromRight.interpolator = AccelerateInterpolator()
        return inFromRight
    }

    fun isAdLoaded():Boolean{
        return isAdloaded
    }








    private fun populateUnifiedNativeAdView(nativeAd: NativeAd, adView: NativeAdView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        val mediaView = adView.findViewById<MediaView>(R.id.media_shop_ad)
        adView.mediaView = mediaView

//        // Set other ad assets.
        adView.headlineView = adView.findViewById(R.id.txt_title_ad)
        adView.bodyView = adView.findViewById(R.id.txt_sub_title_ad)
        adView.callToActionView = adView.findViewById(R.id.txt_buy_ad)
        adView.iconView = adView.findViewById(R.id.icon)
//        adView.priceView = adView.findViewById(R.id.ad_price)
//        adView.starRatingView = adView.findViewById(R.id.ad_stars)
//        adView.storeView = adView.findViewById(R.id.ad_store)
//        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        // The headline is guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
//
//        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
//        // check before trying to display them.
        if (nativeAd.body == null) {
            adView.bodyView?.visibility = View.INVISIBLE
        } else {
            adView.bodyView?.visibility = View.VISIBLE
            (adView.bodyView as TextView).text = nativeAd.body
        }
//
        if (nativeAd.callToAction == null) {
            adView.callToActionView?.visibility = View.INVISIBLE
        } else {
            adView.callToActionView?.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        }
//
        if (nativeAd.icon == null) {
            adView.iconView?.visibility = View.GONE
        } else {
            (adView.iconView as ImageView).setImageDrawable(
                    nativeAd.icon?.drawable)
            adView.iconView?.visibility = View.VISIBLE
        }
//
//        if (nativeAd.price == null) {
//            adView.priceView.visibility = View.INVISIBLE
//        } else {
//            adView.priceView.visibility = View.VISIBLE
//            (adView.priceView as TextView).setText(nativeAd.price)
//        }
//
//        if (nativeAd.store == null) {
//            adView.storeView.visibility = View.INVISIBLE
//        } else {
//            adView.storeView.visibility = View.VISIBLE
//            (adView.storeView as TextView).setText(nativeAd.store)
//        }
//
//        if (nativeAd.starRating == null) {
//            adView.starRatingView.visibility = View.INVISIBLE
//        } else {
//            (adView.starRatingView as RatingBar)
//                    .setRating(nativeAd.starRating!!.toFloat())
//            adView.starRatingView.visibility = View.VISIBLE
//        }
//
//        if (nativeAd.advertiser == null) {
//            adView.advertiserView.visibility = View.INVISIBLE
//        } else {
//            (adView.advertiserView as TextView).setText(nativeAd.advertiser)
//            adView.advertiserView.visibility = View.VISIBLE
//        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd)




    }


    companion object{

        fun newInstance(nativeAd: NativeAd): AdFragment {
            return AdFragment(nativeAd)
        }
    }

}