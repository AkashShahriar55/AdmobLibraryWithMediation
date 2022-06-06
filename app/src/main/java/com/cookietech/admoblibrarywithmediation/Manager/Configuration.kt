package com.cookietech.admoblibrarywithmediation.Manager

class Configuration {
    private var noOfAds = 1;
    private var preload = false
    private var retryTime = 1000;//in millisecond


    fun preload(noOfAds:Int): Configuration {
        this.preload = true
        this.noOfAds = noOfAds;
        return this
    }

    fun setRetryTime(retryTime:Int): Configuration {
        this.retryTime = retryTime
        return this
    }


    fun getNoOfAds():Int{
        return noOfAds
    }

    fun isPreload(): Boolean {
        return preload
    }

    fun getRetryTime(): Int {
        return retryTime
    }
}