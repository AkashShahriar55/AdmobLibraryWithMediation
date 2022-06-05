package com.cookietech.admoblibrarywithmediation.Manager

interface AdLoadListener {

    fun adLoaded(noOfAds:Int)

    fun adLoadFailed(errorMessage:String)

}