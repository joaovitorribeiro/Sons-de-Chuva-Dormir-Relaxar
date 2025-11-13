package com.sonsrelaxantes.chuva.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.sonsrelaxantes.chuva.audio.SoundManager
import com.sonsrelaxantes.chuva.data.SoundItem
import com.sonsrelaxantes.chuva.data.SoundRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PlayerViewModel(app: Application) : AndroidViewModel(app) {
    private val soundManager = SoundManager(app)
    val soundVolumes = mutableStateMapOf<String, Float>()
    val playing = mutableStateMapOf<String, Boolean>()
    val mixSelection = mutableStateMapOf<String, Boolean>()
    val masterVolume = mutableStateOf(1f)

    val timerRunning = mutableStateOf(false)
    val timeRemainingText = mutableStateOf("")
    private var timerJob: Job? = null

    init {
        SoundRepository.sounds.forEach { s ->
            soundVolumes[s.key] = 1f
            playing[s.key] = false
            mixSelection[s.key] = false
        }
    }

    fun togglePlay(item: SoundItem) {
        val key = item.key
        val isPlaying = playing[key] == true
        if (isPlaying) {
            soundManager.stop(key)
            playing[key] = false
        } else {
            val v = soundVolumes[key] ?: 1f
            if (item.assetFileName != null) soundManager.playAsset(key, item.assetFileName, v)
            else if (item.rawRes != null) soundManager.playRaw(key, item.rawRes, v)
            playing[key] = true
        }
    }

    fun setVolume(item: SoundItem, volume: Float) {
        soundVolumes[item.key] = volume
        soundManager.setVolume(item.key, volume)
    }

    fun setMasterVolume(volume: Float) {
        masterVolume.value = volume
        soundManager.setMasterVolume(volume)
    }

    fun playMix(maxSounds: Int = 3) {
        val selected = mixSelection.filter { it.value }.keys.take(maxSounds)
        selected.forEach { key ->
            val s = SoundRepository.sounds.firstOrNull { it.key == key }
            if (s != null) {
                val v = soundVolumes[key] ?: 1f
                if (s.assetFileName != null) soundManager.playAsset(key, s.assetFileName, v)
                else if (s.rawRes != null) soundManager.playRaw(key, s.rawRes, v)
                playing[key] = true
            }
        }
    }

    fun stopMix() {
        soundManager.stopAll()
        playing.keys.forEach { playing[it] = false }
    }

    fun startTimer(minutes: Int) {
        timerJob?.cancel()
        timerRunning.value = true
        timerJob = viewModelScope.launch {
            var remainingMillis = minutes * 60 * 1000L
            while (remainingMillis > 0 && timerRunning.value) {
                val mins = remainingMillis / 60000
                val secs = (remainingMillis % 60000) / 1000
                timeRemainingText.value = String.format("%02d:%02d", mins, secs)
                delay(1000)
                remainingMillis -= 1000
            }
            if (timerRunning.value) {
                stopMix()
                playing.filter { it.value }.keys.forEach { soundManager.stop(it) }
                playing.keys.forEach { playing[it] = false }
                timerRunning.value = false
                timeRemainingText.value = ""
            }
        }
    }

    fun cancelTimer() {
        timerRunning.value = false
        timerJob?.cancel()
        timeRemainingText.value = ""
    }
}
