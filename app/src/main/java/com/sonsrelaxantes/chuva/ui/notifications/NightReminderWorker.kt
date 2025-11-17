package com.sonsrelaxantes.chuva.ui.notifications

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlin.random.Random

class NightReminderWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        val msgs = listOf(
            "Está com insônia? Sons de chuva ajudam a relaxar.",
            "Dificuldade para dormir? Experimente chuva suave.",
            "Hora de desacelerar. Ouça chuva para dormir melhor.",
            "Sono leve? Um som de chuva pode acalmar.",
            "Prepare o descanso: chuva relaxante para a noite."
        )
        NotificationUtils.show(applicationContext, applicationContext.getString(com.sonsrelaxantes.chuva.R.string.title_main), msgs[Random.nextInt(msgs.size)], Random.nextInt())
        NightReminderScheduler.scheduleNext(applicationContext)
        return Result.success()
    }
}