package com.sonsrelaxantes.chuva.ui.util

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun isWide(content: @Composable (Boolean) -> Unit) {
    BoxWithConstraints {
        val wide = remember(maxWidth) { maxWidth >= 600.dp }
        content(wide)
    }
}
