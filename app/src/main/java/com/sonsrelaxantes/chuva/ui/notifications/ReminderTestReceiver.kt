package com.sonsrelaxantes.chuva.ui.notifications

import android.content.BroadcastReceiver
import android.content.Context
import kotlin.random.Random

class ReminderTestReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: android.content.Intent) {
        NotificationUtils.createChannel(context)
        val title = context.getString(com.sonsrelaxantes.chuva.R.string.title_main)
        val msg = "Teste de notificação: sons de chuva para dormir"
        NotificationUtils.show(context, title, msg, Random.nextInt())
    }
}