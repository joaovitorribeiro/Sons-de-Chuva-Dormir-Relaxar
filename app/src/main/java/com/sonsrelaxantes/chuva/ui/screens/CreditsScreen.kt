package com.sonsrelaxantes.chuva.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreditsScreen(navController: NavController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Créditos de Áudio") }) }) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text("Gentle Rain Loop — OrangeFreeSounds (CC BY 4.0)")
            Text("Rain Normal — OrangeFreeSounds (CC BY 4.0)")
            Text("Thunder and Rain — OrangeFreeSounds (CC BY 4.0)")
        }
    }
}
