package com.cookietech.android_ads_library.Manager

interface AdLoadListener {

    fun adLoaded(noOfAds:Int)

    fun adLoadFailed(errorMessage:String)

}