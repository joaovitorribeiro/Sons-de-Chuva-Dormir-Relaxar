package com.sonsrelaxantes.chuva.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sonsrelaxantes.chuva.data.SoundRepository
import com.sonsrelaxantes.chuva.ui.components.AdBanner
import com.sonsrelaxantes.chuva.ui.util.isWide
import com.sonsrelaxantes.chuva.viewmodel.PlayerViewModel
import com.sonsrelaxantes.chuva.ui.components.ShowInterstitial

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MixScreen(navController: NavController, vm: PlayerViewModel, onOpenDrawer: () -> Unit) {
    val showPlayAd = remember { mutableStateOf(false) }
    var lastAdShown by remember { mutableStateOf(0L) }
    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Mix de Sons") }, navigationIcon = { IconButton(onClick = onOpenDrawer) { Text("≡") } }) },
        bottomBar = { AdBanner(modifier = Modifier.fillMaxWidth().navigationBarsPadding(), adUnitId = com.sonsrelaxantes.chuva.R.string.admob_banner_id.let { id -> com.sonsrelaxantes.chuva.ui.util.StringRes.get(id) }) },
        contentWindowInsets = WindowInsets.safeDrawing
    ) { padding ->
        isWide { wide ->
            if (wide) {
                Row(Modifier.padding(padding).fillMaxSize()) {
                    LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(SoundRepository.sounds) { item ->
                            ElevatedCard(Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
                                Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(item.name)
                                    val checked = vm.mixSelection[item.key] == true
                                    Checkbox(checked = checked, onCheckedChange = { c -> vm.mixSelection[item.key] = c })
                                }
                                var vol by remember { mutableStateOf(vm.soundVolumes[item.key] ?: 1f) }
                                Slider(value = vol, onValueChange = { v -> vol = v; vm.setVolume(item, v) }, valueRange = 0f..1f)
                            }
                        }
                    }
                    Column(Modifier.width(280.dp).padding(12.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = {
                            val now = System.currentTimeMillis()
                            val shouldAd = (now - lastAdShown > 60000)
                            vm.playMix()
                            if (shouldAd) { showPlayAd.value = true; lastAdShown = now }
                        }, modifier = Modifier.fillMaxWidth()) { Text("Tocar Combinação") }
                        Button(onClick = { vm.stopMix() }, modifier = Modifier.fillMaxWidth()) { Text("Parar") }
                    }
                }
            } else {
                Column(Modifier.padding(padding).fillMaxSize()) {
                    LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(SoundRepository.sounds) { item ->
                            ElevatedCard(Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
                                Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(item.name)
                                    val checked = vm.mixSelection[item.key] == true
                                    Checkbox(checked = checked, onCheckedChange = { c -> vm.mixSelection[item.key] = c })
                                }
                                var vol by remember { mutableStateOf(vm.soundVolumes[item.key] ?: 1f) }
                                Slider(value = vol, onValueChange = { v -> vol = v; vm.setVolume(item, v) }, valueRange = 0f..1f)
                            }
                        }
                    }
                    Row(Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Button(onClick = {
                            val now = System.currentTimeMillis()
                            val shouldAd = (now - lastAdShown > 60000)
                            vm.playMix()
                            if (shouldAd) { showPlayAd.value = true; lastAdShown = now }
                        }, modifier = Modifier.weight(1f)) { Text("Tocar Combinação") }
                        Button(onClick = { vm.stopMix() }, modifier = Modifier.weight(1f)) { Text("Parar") }
                    }
                }
            }
        }
    }
    ShowInterstitial(activity = (navController.context as android.app.Activity), adUnitId = com.sonsrelaxantes.chuva.ui.util.StringRes.get(com.sonsrelaxantes.chuva.R.string.admob_interstitial_id), trigger = showPlayAd.value, onShown = { showPlayAd.value = false }, onFailed = { showPlayAd.value = false })
}
