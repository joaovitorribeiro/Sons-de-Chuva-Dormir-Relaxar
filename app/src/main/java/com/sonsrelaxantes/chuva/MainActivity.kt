package com.sonsrelaxantes.chuva

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sonsrelaxantes.chuva.navigation.Routes
import com.sonsrelaxantes.chuva.ui.components.AppDrawer
import com.sonsrelaxantes.chuva.ui.screens.HomeScreen
import com.sonsrelaxantes.chuva.ui.screens.MixScreen
import com.sonsrelaxantes.chuva.ui.screens.PrivacyScreen
import com.sonsrelaxantes.chuva.ui.screens.CreditsScreen
import com.sonsrelaxantes.chuva.ui.screens.SettingsScreen
import com.sonsrelaxantes.chuva.ui.screens.SplashScreen
import com.sonsrelaxantes.chuva.ui.screens.TimerScreen
import com.sonsrelaxantes.chuva.ui.theme.SonsTheme
import com.sonsrelaxantes.chuva.viewmodel.PlayerViewModel
import com.sonsrelaxantes.chuva.viewmodel.ThemeViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App() }
    }
}

@Composable
fun App() {
    val themeViewModel: ThemeViewModel = viewModel()
    SonsTheme(darkTheme = themeViewModel.dark.value) {
        Surface(color = MaterialTheme.colorScheme.background) {
            val navController = rememberNavController()
            val playerViewModel: PlayerViewModel = viewModel()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            ModalNavigationDrawer(drawerState = drawerState, drawerContent = { AppDrawer(navController) { scope.launch { drawerState.close() } } }) {
                Navigation(navController, playerViewModel, themeViewModel) { scope.launch { drawerState.open() } }
            }
        }
    }
}

@Composable
fun Navigation(navController: NavHostController, playerViewModel: PlayerViewModel, themeViewModel: ThemeViewModel, onOpenDrawer: () -> Unit) {
    NavHost(navController = navController, startDestination = Routes.Splash.route) {
        composable(Routes.Splash.route) { SplashScreen(onFinish = { navController.navigate(Routes.Home.route) { popUpTo(Routes.Splash.route) { inclusive = true } } }) }
        composable(Routes.Home.route) { HomeScreen(navController, playerViewModel, onOpenDrawer) }
        composable(Routes.Mix.route) { MixScreen(navController, playerViewModel, onOpenDrawer) }
        composable(Routes.Timer.route) { TimerScreen(navController, playerViewModel, onOpenDrawer) }
        composable(Routes.Settings.route) { SettingsScreen(navController, playerViewModel, themeViewModel) }
        composable(Routes.Privacy.route) { PrivacyScreen(navController) }
        composable(Routes.Credits.route) { CreditsScreen(navController) }
    }
}
