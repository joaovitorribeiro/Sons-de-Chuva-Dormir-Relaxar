package com.sonsrelaxantes.chuva.audio

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlin.concurrent.thread
import kotlin.math.max
import kotlin.math.min

class NoiseEngine {
    private data class NoisePlayer(val track: AudioTrack, var volume: Float, var running: Boolean)
    private val players = mutableMapOf<String, NoisePlayer>()
    private var masterVolume = 1f

    fun playWhite(key: String, volume: Float = 1f) {
        startNoise(key, volume) { whiteSample() }
    }

    fun playPink(key: String, volume: Float = 1f) {
        var b0 = 0.0
        var b1 = 0.0
        var b2 = 0.0
        var b3 = 0.0
        var b4 = 0.0
        var b5 = 0.0
        var b6 = 0.0
        startNoise(key, volume) {
            val white = Math.random() * 2 - 1
            b0 = 0.99886 * b0 + white * 0.0555179
            b1 = 0.99332 * b1 + white * 0.0750759
            b2 = 0.96900 * b2 + white * 0.1538520
            b3 = 0.86650 * b3 + white * 0.3104856
            b4 = 0.55000 * b4 + white * 0.5329522
            b5 = -0.7616 * b5 - white * 0.0168980
            val sample = b0 + b1 + b2 + b3 + b4 + b5 + b6 + white * 0.5362
            b6 = white * 0.115926
            sample
        }
    }

    fun playBrown(key: String, volume: Float = 1f) {
        var lastOut = 0.0
        startNoise(key, volume) {
            val white = Math.random() * 2 - 1
            var out = lastOut + (white / 5.0)
            out = max(-1.0, min(1.0, out))
            lastOut = out
            out
        }
    }

    fun stop(key: String) {
        players.remove(key)?.let {
            it.running = false
            it.track.stop()
            it.track.release()
        }
    }

    fun stopAll() {
        players.keys.toList().forEach { stop(it) }
    }

    fun setVolume(key: String, volume: Float) {
        players[key]?.let {
            it.volume = volume
            val v = (volume * masterVolume).coerceIn(0f, 1f)
            it.track.setVolume(v)
        }
    }

    fun setMasterVolume(volume: Float) {
        masterVolume = volume
        players.values.forEach { p -> p.track.setVolume((p.volume * masterVolume).coerceIn(0f, 1f)) }
    }

    private fun startNoise(key: String, volume: Float, sampleFn: () -> Double) {
        stop(key)
        val sampleRate = 44100
        val minBuf = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT)
        val track = AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, minBuf, AudioTrack.MODE_STREAM)
        val player = NoisePlayer(track, volume, true)
        players[key] = player
        track.play()
        thread(start = true) {
            val buf = ShortArray(minBuf)
            while (player.running) {
                val v = (player.volume * masterVolume).coerceIn(0f, 1f)
                for (i in buf.indices) {
                    val s = (sampleFn() * 32767.0 * v).toInt().coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt())
                    buf[i] = s.toShort()
                }
                track.write(buf, 0, buf.size)
            }
        }
    }

    private fun whiteSample(): Double {
        return Math.random() * 2 - 1
    }
}
