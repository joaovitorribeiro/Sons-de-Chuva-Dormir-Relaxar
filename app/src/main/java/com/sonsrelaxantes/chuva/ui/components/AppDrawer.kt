package com.sonsrelaxantes.chuva.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sonsrelaxantes.chuva.navigation.Routes

@Composable
fun AppDrawer(navController: NavController, onClose: () -> Unit) {
    ModalDrawerSheet(drawerContainerColor = DrawerDefaults.containerColor) {
        Column(Modifier.padding(12.dp)) {
            Text(text = "Sons de Chuva e Natureza")
            Divider(Modifier.padding(vertical = 8.dp))
            NavigationDrawerItem(
                label = { Text("Home") },
                selected = false,
                onClick = { navController.navigate(Routes.Home.route); onClose() },
                modifier = Modifier.fillMaxWidth(),
                colors = NavigationDrawerItemDefaults.colors()
            )
            NavigationDrawerItem(
                label = { Text("Mix de Sons") },
                selected = false,
                onClick = { navController.navigate(Routes.Mix.route); onClose() },
                modifier = Modifier.fillMaxWidth(),
                colors = NavigationDrawerItemDefaults.colors()
            )
            NavigationDrawerItem(
                label = { Text("Timer para Dormir") },
                selected = false,
                onClick = { navController.navigate(Routes.Timer.route); onClose() },
                modifier = Modifier.fillMaxWidth(),
                colors = NavigationDrawerItemDefaults.colors()
            )
            NavigationDrawerItem(
                label = { Text("Configurações") },
                selected = false,
                onClick = { navController.navigate(Routes.Settings.route); onClose() },
                modifier = Modifier.fillMaxWidth(),
                colors = NavigationDrawerItemDefaults.colors()
            )
            NavigationDrawerItem(
                label = { Text("Política de Privacidade") },
                selected = false,
                onClick = { navController.navigate(Routes.Privacy.route); onClose() },
                modifier = Modifier.fillMaxWidth(),
                colors = NavigationDrawerItemDefaults.colors()
            )
            NavigationDrawerItem(
                label = { Text("Créditos de Áudio") },
                selected = false,
                onClick = { navController.navigate(Routes.Credits.route); onClose() },
                modifier = Modifier.fillMaxWidth(),
                colors = NavigationDrawerItemDefaults.colors()
            )
        }
    }
}
