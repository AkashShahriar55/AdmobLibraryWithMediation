package com.cookietech.admoblibrarywithmediation.Manager

import android.content.Context

class AdRequestBuilder<T:AdsProvider>(val builder: AdsManager.Builder, context: Context, unitId: String) {



    fun build(){
        builder
    }

}