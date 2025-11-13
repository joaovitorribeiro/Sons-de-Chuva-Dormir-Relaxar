package com.sonsrelaxantes.chuva.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sonsrelaxantes.chuva.ui.components.ShowInterstitial
import com.sonsrelaxantes.chuva.ui.util.isWide
import com.sonsrelaxantes.chuva.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen(navController: NavController, vm: PlayerViewModel, onOpenDrawer: () -> Unit) {
    val ctx = LocalContext.current
    val act = ctx as Activity
    val showing = remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = { Text("Timer para Dormir") }, navigationIcon = { IconButton(onClick = onOpenDrawer) { Text("≡") } }) }, contentWindowInsets = WindowInsets.safeDrawing) { padding ->
        isWide { wide ->
            if (wide) {
                Row(Modifier.padding(padding).padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(Modifier.weight(1f)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = { showing.value = true; vm.startTimer(10) }, modifier = Modifier.weight(1f)) { Text("10 minutos") }
                            Button(onClick = { showing.value = true; vm.startTimer(20) }, modifier = Modifier.weight(1f)) { Text("20 minutos") }
                        }
                        Spacer(Modifier.height(8.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = { showing.value = true; vm.startTimer(30) }, modifier = Modifier.weight(1f)) { Text("30 minutos") }
                            Button(onClick = { showing.value = true; vm.startTimer(60) }, modifier = Modifier.weight(1f)) { Text("1 hora") }
                        }
                    }
                    Column(Modifier.weight(1f)) {
                        val custom = remember { mutableStateOf(30) }
                        OutlinedTextField(value = custom.value.toString(), onValueChange = { v -> custom.value = v.toIntOrNull() ?: custom.value }, label = { Text("Minutos") }, modifier = Modifier.fillMaxWidth())
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { showing.value = true; vm.startTimer(custom.value) }, modifier = Modifier.fillMaxWidth()) { Text("Iniciar") }
                        Spacer(Modifier.height(12.dp))
                        if (vm.timerRunning.value) Text(text = "Som desligará em: ${vm.timeRemainingText.value}")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { vm.cancelTimer() }, modifier = Modifier.fillMaxWidth()) { Text("Cancelar Timer") }
                    }
                }
            } else {
                Column(Modifier.padding(padding).padding(16.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = { showing.value = true; vm.startTimer(10) }) { Text("10 minutos") }
                        Button(onClick = { showing.value = true; vm.startTimer(20) }) { Text("20 minutos") }
                    }
                    Spacer(Modifier.height(8.dp))
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Button(onClick = { showing.value = true; vm.startTimer(30) }) { Text("30 minutos") }
                        Button(onClick = { showing.value = true; vm.startTimer(60) }) { Text("1 hora") }
                    }
                    Spacer(Modifier.height(16.dp))
                    val custom = remember { mutableStateOf(30) }
                    OutlinedTextField(value = custom.value.toString(), onValueChange = { v -> custom.value = v.toIntOrNull() ?: custom.value }, label = { Text("Minutos") })
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { showing.value = true; vm.startTimer(custom.value) }) { Text("Iniciar") }
                    Spacer(Modifier.height(12.dp))
                    if (vm.timerRunning.value) Text(text = "Som desligará em: ${vm.timeRemainingText.value}")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { vm.cancelTimer() }) { Text("Cancelar Timer") }
                }
            }
        }
    }
    ShowInterstitial(activity = act, adUnitId = com.sonsrelaxantes.chuva.ui.util.StringRes.get(com.sonsrelaxantes.chuva.R.string.admob_interstitial_id), trigger = showing.value, onShown = { showing.value = false }, onFailed = { showing.value = false })
}
