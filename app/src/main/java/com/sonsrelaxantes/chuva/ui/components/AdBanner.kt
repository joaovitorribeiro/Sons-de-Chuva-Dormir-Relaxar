package com.sonsrelaxantes.chuva.ui.components

import android.widget.FrameLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdBanner(modifier: Modifier = Modifier, adUnitId: String) {
    AndroidView(modifier = modifier, factory = { context ->
        val adView = AdView(context)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = adUnitId
        val container = FrameLayout(context)
        container.addView(adView)
        adView.loadAd(AdRequest.Builder().build())
        container
    })
}
