package com.rosberry.barcodescanner.scanner

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface ScannerView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun resumeCamera(scannerActive: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun vibrate()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayBarCode(text: String)
}
