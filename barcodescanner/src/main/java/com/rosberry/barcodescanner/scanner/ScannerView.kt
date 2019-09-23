package com.rosberry.barcodescanner.scanner

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

interface ScannerView : MvpView {
    @StateStrategyType(SkipStrategy::class)
    fun resumeCamera(scannerActive: Boolean)

    @StateStrategyType(SkipStrategy::class)
    fun vibrate()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun displayBarCode(text: String)
}
