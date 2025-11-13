package com.sonsrelaxantes.chuva.ui.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

@Composable
fun ShowInterstitial(activity: Activity, adUnitId: String, trigger: Boolean, onShown: () -> Unit, onFailed: () -> Unit) {
    LaunchedEffect(trigger) {
        if (trigger) {
            InterstitialAd.load(activity, adUnitId, AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    ad.show(activity)
                    onShown()
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    onFailed()
                }
            })
        }
    }
}
