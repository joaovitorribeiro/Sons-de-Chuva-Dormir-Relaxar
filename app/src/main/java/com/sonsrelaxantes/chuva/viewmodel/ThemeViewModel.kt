package com.sonsrelaxantes.chuva.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf

class ThemeViewModel : ViewModel() {
    val dark = mutableStateOf(false)
}
