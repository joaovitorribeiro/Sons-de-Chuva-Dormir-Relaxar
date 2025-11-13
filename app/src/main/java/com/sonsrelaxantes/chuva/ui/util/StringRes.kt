package com.sonsrelaxantes.chuva.ui.util

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

object StringRes {
    @Composable
    fun get(id: Int): String {
        val ctx = LocalContext.current
        return ctx.getString(id)
    }
}
