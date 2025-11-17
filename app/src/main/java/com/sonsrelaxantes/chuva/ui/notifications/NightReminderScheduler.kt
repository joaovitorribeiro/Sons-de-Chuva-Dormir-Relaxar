package com.sonsrelaxantes.chuva.ui.notifications

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

object NightReminderScheduler {
    private val times = listOf(22 to 0, 23 to 30, 1 to 0)
    fun scheduleInitial(ctx: Context) {
        times.forEachIndexed { idx, t ->
            val delay = nextDelayMillis(t.first, t.second)
            val req = OneTimeWorkRequestBuilder<NightReminderWorker>().setInitialDelay(java.time.Duration.ofMillis(delay)).addTag("night-reminder-$idx").build()
            WorkManager.getInstance(ctx).enqueueUniqueWork("night-reminder-$idx", androidx.work.ExistingWorkPolicy.REPLACE, req)
        }
    }
    fun scheduleNext(ctx: Context) {
        val now = LocalDateTime.now()
        val next = now.plusDays(1)
        val hour = times.random().first
        val minute = times.random().second
        val target = next.withHour(hour).withMinute(minute).withSecond(0).withNano(0)
        val millis = ChronoUnit.MILLIS.between(LocalDateTime.now(), target)
        val req = OneTimeWorkRequestBuilder<NightReminderWorker>().setInitialDelay(java.time.Duration.ofMillis(millis)).addTag("night-reminder-next").build()
        WorkManager.getInstance(ctx).enqueueUniqueWork("night-reminder-next", androidx.work.ExistingWorkPolicy.REPLACE, req)
    }
    private fun nextDelayMillis(hour: Int, minute: Int): Long {
        val now = LocalDateTime.now()
        var target = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0)
        if (!target.isAfter(now)) target = target.plusDays(1)
        return ChronoUnit.MILLIS.between(now, target)
    }
}