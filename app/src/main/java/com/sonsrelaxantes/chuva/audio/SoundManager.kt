package com.sonsrelaxantes.chuva.audio

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import androidx.annotation.RawRes
import com.sonsrelaxantes.chuva.data.SoundRepository

class SoundManager(private val context: Context) {
    private val players = mutableMapOf<String, MediaPlayer>()
    private var masterVolume = 1f
    private val noise = NoiseEngine()

    fun playAsset(key: String, assetFileName: String, volume: Float = 1f) {
        stop(key)
        try {
            val afd = context.assets.openFd(assetFileName)
            val p = MediaPlayer()
            p.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            p.isLooping = true
            p.prepare()
            p.setVolume(volume * masterVolume, volume * masterVolume)
            p.start()
            players[key] = p
        } catch (_: Exception) {
            playSynthetic(key, volume)
        }
    }

    fun playRaw(key: String, @RawRes resId: Int, volume: Float = 1f) {
        stop(key)
        try {
            val p = MediaPlayer.create(context, resId)
            if (p != null) {
                p.isLooping = true
                p.setVolume(volume * masterVolume, volume * masterVolume)
                p.start()
                players[key] = p
            } else {
                playSynthetic(key, volume)
            }
        } catch (_: Exception) {
            playSynthetic(key, volume)
        }
    }

    fun stop(key: String) {
        players.remove(key)?.let {
            if (it.isPlaying) it.stop()
            it.release()
        }
        noise.stop(key)
    }

    fun setVolume(key: String, volume: Float) {
        players[key]?.setVolume(volume * masterVolume, volume * masterVolume)
        noise.setVolume(key, volume)
    }

    fun setMasterVolume(volume: Float) {
        masterVolume = volume
        players.forEach { (_, p) -> p.setVolume(masterVolume, masterVolume) }
        noise.setMasterVolume(volume)
    }

    fun isPlaying(key: String): Boolean = players[key]?.isPlaying == true

    fun stopAll() {
        players.keys.toList().forEach { stop(it) }
        noise.stopAll()
    }

    private fun playSynthetic(key: String, volume: Float) {
        when (key) {
            "chuva_suave" -> noise.playPink(key, volume)
            "chuva_forte" -> noise.playBrown(key, volume)
            "janela" -> noise.playPink(key, volume)
            "floresta" -> noise.playBrown(key, volume)
            "oceano" -> noise.playWhite(key, volume)
            else -> noise.playWhite(key, volume)
        }
    }
}
