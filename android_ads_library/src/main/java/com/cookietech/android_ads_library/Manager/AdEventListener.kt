package com.cookietech.android_ads_library.Manager

import com.google.android.gms.ads.AdError

open class AdEventListener {

    open fun onAdShowedFullScreenContent() {

    }

    open fun onAdFailedToShowFullScreenContent(p0: AdError) {

    }


    open fun onAdDismissedFullScreenContent() {

    }

    open fun onAdClicked() {

    }

    open fun onAdImpression() {

    }

}