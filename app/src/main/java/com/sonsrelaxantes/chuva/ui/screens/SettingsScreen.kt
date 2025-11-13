package com.sonsrelaxantes.chuva.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sonsrelaxantes.chuva.navigation.Routes
import com.sonsrelaxantes.chuva.ui.util.isWide
import com.sonsrelaxantes.chuva.viewmodel.PlayerViewModel
import com.sonsrelaxantes.chuva.viewmodel.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, vm: PlayerViewModel, themeViewModel: ThemeViewModel) {
    val ctx = LocalContext.current
    Scaffold(topBar = { TopAppBar(title = { Text("Configurações") }, navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Text("≡") } }) }, contentWindowInsets = WindowInsets.safeDrawing) { padding ->
        isWide { wide ->
            if (wide) {
                Row(Modifier.padding(padding).padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(Modifier.weight(1f)) {
                        Text("Volume Geral do App")
                        Slider(value = vm.masterVolume.value, onValueChange = { vm.setMasterVolume(it) }, valueRange = 0f..1f, modifier = Modifier.fillMaxWidth())
                        Button(onClick = { navController.navigate(Routes.Privacy.route) }, modifier = Modifier.fillMaxWidth()) { Text("Política de Privacidade") }
                    }
                    Column(Modifier.weight(1f)) {
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Modo escuro")
                            androidx.compose.material3.Switch(checked = themeViewModel.dark.value, onCheckedChange = { themeViewModel.dark.value = it })
                        }
                        Button(onClick = {
                            val uri = Uri.parse("market://details?id=" + ctx.packageName)
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            try { ctx.startActivity(intent) } catch (e: Exception) {
                                val web = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + ctx.packageName))
                                web.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                ctx.startActivity(web)
                            }
                        }, modifier = Modifier.fillMaxWidth()) { Text("Avalie o App na Play Store") }
                        Text("Nome do desenvolvedor: Desenvolvedor")
                        Text("Versão do app: 1.0.0")
                    }
                }
            } else {
                Column(Modifier.padding(padding).padding(16.dp)) {
                    Text("Volume Geral do App")
                    Slider(value = vm.masterVolume.value, onValueChange = { vm.setMasterVolume(it) }, valueRange = 0f..1f, modifier = Modifier.fillMaxWidth())
                    Button(onClick = { navController.navigate(Routes.Privacy.route) }) { Text("Política de Privacidade") }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Modo escuro")
                        androidx.compose.material3.Switch(checked = themeViewModel.dark.value, onCheckedChange = { themeViewModel.dark.value = it })
                    }
                    Button(onClick = {
                        val uri = Uri.parse("market://details?id=" + ctx.packageName)
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        try { ctx.startActivity(intent) } catch (e: Exception) {
                            val web = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + ctx.packageName))
                            web.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            ctx.startActivity(web)
                        }
                    }) { Text("Avalie o App na Play Store") }
                    Text("Nome do desenvolvedor: Desenvolvedor")
                    Text("Versão do app: 1.0.0")
                }
            }
        }
    }
}
