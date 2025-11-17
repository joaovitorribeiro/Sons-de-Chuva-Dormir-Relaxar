package com.sonsrelaxantes.chuva

import android.app.Application
import com.sonsrelaxantes.chuva.ui.notifications.NotificationUtils
import com.sonsrelaxantes.chuva.ui.notifications.NightReminderScheduler
import com.google.android.gms.ads.MobileAds

class SonsApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
        NotificationUtils.createChannel(this)
        NightReminderScheduler.scheduleInitial(this)
    }
}
