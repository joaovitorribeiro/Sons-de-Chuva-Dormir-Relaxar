package com.sonsrelaxantes.chuva.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sonsrelaxantes.chuva.data.SoundRepository
import com.sonsrelaxantes.chuva.navigation.Routes
import com.sonsrelaxantes.chuva.ui.components.AdBanner
import com.sonsrelaxantes.chuva.ui.util.isWide
import com.sonsrelaxantes.chuva.viewmodel.PlayerViewModel
import com.sonsrelaxantes.chuva.ui.components.ShowInterstitial

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, vm: PlayerViewModel, onOpenDrawer: () -> Unit) {
    val showPlayAd = remember { mutableStateOf(false) }
    var lastAdShown by remember { mutableStateOf(0L) }
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Sons de Chuva e Natureza") }, navigationIcon = { IconButton(onClick = onOpenDrawer) { Text("≡") } }) },
        bottomBar = { AdBanner(modifier = Modifier.fillMaxWidth().navigationBarsPadding(), adUnitId = com.sonsrelaxantes.chuva.R.string.admob_banner_id.let { id -> com.sonsrelaxantes.chuva.ui.util.StringRes.get(id) }) },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->
        isWide { wide ->
            if (wide) {
                Row(Modifier.padding(padding).fillMaxSize()) {
                    var query by remember { mutableStateOf("") }
                    OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Buscar som") }, modifier = Modifier.padding(horizontal = 12.dp))
                    val filtered = remember(query, vm.playing.keys) {
                        SoundRepository.sounds.filter { it.name.contains(query, ignoreCase = true) }
                    }
                    LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(filtered) { item ->
                            ElevatedCard(Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
                                Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Row(Modifier.weight(1f)) {
                                        Icon(item.icon, contentDescription = item.name)
                                        Spacer(Modifier.width(8.dp))
                                        Text(item.name)
                                    }
                                    val isPlaying = vm.playing[item.key] == true
                                    IconButton(onClick = {
                                        val now = System.currentTimeMillis()
                                        val shouldAd = !isPlaying && (now - lastAdShown > 60000)
                                        vm.togglePlay(item)
                                        if (shouldAd) { showPlayAd.value = true; lastAdShown = now }
                                    }) { Icon(if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow, contentDescription = "playpause") }
                                }
                                var vol by remember { mutableStateOf(vm.soundVolumes[item.key] ?: 1f) }
                                Slider(value = vol, onValueChange = { v -> vol = v; vm.setVolume(item, v) }, valueRange = 0f..1f)
                            }
                        }
                    }
                    Column(Modifier.width(280.dp).padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = { navController.navigate(Routes.Mix.route) }, modifier = Modifier.fillMaxWidth()) { Text("Mixar Sons") }
                        Button(onClick = { navController.navigate(Routes.Timer.route) }, modifier = Modifier.fillMaxWidth()) { Text("Timer para Dormir") }
                    }
                }
            } else {
                Column(Modifier.padding(padding).fillMaxSize()) {
                    var query by remember { mutableStateOf("") }
                    OutlinedTextField(value = query, onValueChange = { query = it }, label = { Text("Buscar som") }, modifier = Modifier.padding(horizontal = 12.dp))
                    val filtered = remember(query, vm.playing.keys) {
                        SoundRepository.sounds.filter { it.name.contains(query, ignoreCase = true) }
                    }
                    LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(filtered) { item ->
                            ElevatedCard(Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
                                Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Row(Modifier.weight(1f)) {
                                        Icon(item.icon, contentDescription = item.name)
                                        Spacer(Modifier.width(8.dp))
                                        Text(item.name)
                                    }
                                    val isPlaying = vm.playing[item.key] == true
                                    IconButton(onClick = {
                                        val now = System.currentTimeMillis()
                                        val shouldAd = !isPlaying && (now - lastAdShown > 60000)
                                        vm.togglePlay(item)
                                        if (shouldAd) { showPlayAd.value = true; lastAdShown = now }
                                    }) { Icon(if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow, contentDescription = "playpause") }
                                }
                                var vol by remember { mutableStateOf(vm.soundVolumes[item.key] ?: 1f) }
                                Slider(value = vol, onValueChange = { v -> vol = v; vm.setVolume(item, v) }, valueRange = 0f..1f)
                            }
                        }
                    }
                    Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = { navController.navigate(Routes.Mix.route) }, modifier = Modifier.weight(1f)) { Text("Mixar Sons") }
                        Button(onClick = { navController.navigate(Routes.Timer.route) }, modifier = Modifier.weight(1f)) { Text("Timer para Dormir") }
                    }
                }
            }
        }
    }
    ShowInterstitial(activity = (navController.context as android.app.Activity), adUnitId = com.sonsrelaxantes.chuva.ui.util.StringRes.get(com.sonsrelaxantes.chuva.R.string.admob_interstitial_id), trigger = showPlayAd.value, onShown = { showPlayAd.value = false }, onFailed = { showPlayAd.value = false })
}
