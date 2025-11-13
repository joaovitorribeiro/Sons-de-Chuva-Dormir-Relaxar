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
        SoundItem("chuva_suave", "Chuva Suave", "gentle_rain_soft.mp3", null, Icons.Filled.Cloud),
        SoundItem("chuva_forte", "Chuva Forte", "rain_sound_mp3.mp3", null, Icons.Filled.Cloud),
        SoundItem("chuva_trovao", "Chuva com Trovão", "rain_thunder.mp3", null, Icons.Filled.Cloud),
        SoundItem("oceano_ondas", "Oceano (Ondas)", "waves_mp3.mp3", null, Icons.Filled.Water),
        SoundItem("floresta_relax", "Floresta Relaxante", "relaxing_forest.mp3", null, Icons.Filled.Forest),
        SoundItem("floresta_tropical", "Floresta Tropical", "rainforest_sounds.mp3", null, Icons.Filled.Forest),
        SoundItem("floresta_noite", "Floresta à Noite", "forest_at_night.mp3", null, Icons.Filled.Forest),
        SoundItem("rio_calmo", "Rio Calmo (Música)", "quiet_river.mp3", null, Icons.Filled.Water)
    )
}
