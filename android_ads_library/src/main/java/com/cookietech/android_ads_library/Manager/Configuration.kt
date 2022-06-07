package com.cookietech.android_ads_library.Manager


class Configuration {

    private var noOfAds = 1;
    private var preload = false
    // how long before the data source tries to reconnect to Google play
    private var retryMilliseconds = RETRY_TIMER_START_MILLISECONDS
    private var exponentialBackOff = true
    private var maxNoOfRetry = 10;


    fun preload(noOfAds:Int): Configuration {
        this.preload = true
        this.noOfAds = noOfAds;
        return this
    }



    fun linearRetryTime(retryTime:Long,maxNoOfRetry:Int): Configuration {
        this.exponentialBackOff = false
        this.maxNoOfRetry = maxNoOfRetry
        this.retryMilliseconds = retryTime
        return this
    }

    fun isExponentialBackOff():Boolean{
        return exponentialBackOff;
    }


    fun getNoOfAds():Int{
        return noOfAds
    }

    fun isPreload(): Boolean {
        return preload
    }

    fun getRetryTime(): Long {
        return retryMilliseconds
    }

    fun getMaxNoOfRetries(): Int {
        return maxNoOfRetry
    }

    companion object {
         const val RETRY_TIMER_START_MILLISECONDS = 1L * 10000L
         const val RETRY_TIMER_MAX_TIME_MILLISECONDS = 1000L * 60L * 5L // 15 minutes
    }
}