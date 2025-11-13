package com.sonsrelaxantes.chuva.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.Forest
import androidx.compose.ui.graphics.vector.ImageVector

data class SoundItem(
    val key: String,
    val name: String,
    val assetFileName: String?,
    val rawRes: Int?,
    val icon: ImageVector
)

object SoundRepository {
    val sounds = listOf(
        SoundItem("chuva_normal", "Chuva", "rain_normal.mp3", null, Icons.Filled.Cloud),
        SoundItem("chuva_forte", "Chuva Forte", "rain_sound_mp3.mp3", null, Icons.Filled.Cloud),
        SoundItem("chuva_trovao", "Chuva com Trovão", "rain_thunder.mp3", null, Icons.Filled.Cloud)
    )
}
