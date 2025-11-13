package com.sonsrelaxantes.chuva

import android.app.Application
import com.google.android.gms.ads.MobileAds

class SonsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}
