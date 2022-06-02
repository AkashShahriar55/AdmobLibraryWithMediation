package com.cookietech.admoblibrarywithmediation.Manager

import androidx.lifecycle.Lifecycle

interface AdsProvider {
    fun addObserver(lifecycle: Lifecycle):AdsProvider
    fun load():AdsProvider
}