package com.cookietech.admoblibrarywithmediation.Manager

class Configuration {
    private var noOfAds = 1;
    private var preload = false


    fun preload(noOfAds:Int): Configuration {
        this.preload = true
        this.noOfAds = noOfAds;
        return this
    }


    fun getNoOfAds():Int{
        return noOfAds
    }

    fun isPreload(): Boolean {
        return preload
    }
}