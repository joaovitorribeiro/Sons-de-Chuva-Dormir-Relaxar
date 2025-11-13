package com.sonsrelaxantes.chuva.ui.screens

import android.webkit.WebView
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.sonsrelaxantes.chuva.R
import com.sonsrelaxantes.chuva.ui.util.StringRes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyScreen(navController: NavController) {
    val privacyUrl = StringRes.get(R.string.privacy_url)
    Scaffold(topBar = { TopAppBar(title = { Text("Política de Privacidade") }) }) { padding ->
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                val wv = WebView(context)
                wv.webViewClient = object : WebViewClient() {
                    override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                        view.loadUrl("file:///android_asset/privacy.html")
                    }
                    override fun onPageFinished(view: WebView, url: String) {
                        view.evaluateJavascript("(function(){var t='';try{t=(document.body&&document.body.innerText)||'';}catch(e){}return (t||'').trim().length;})()") { res ->
                            val len = res?.replace("\"", "")?.toIntOrNull() ?: -1
                            if (len <= 0) {
                                view.loadUrl("file:///android_asset/privacy.html")
                            }
                        }
                    }
                }
                wv.settings.javaScriptEnabled = true
                wv.loadUrl(privacyUrl)
                wv
            }
        )
    }
}
