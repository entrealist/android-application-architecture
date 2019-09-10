package com.rosberry.barcodescanner.scanner

import com.google.zxing.Result
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ScannerPresenter : MvpPresenter<ScannerView>() {

    fun onBarcodeReady(result: Result?) {
        if (result != null) {
            viewState.vibrate()
            viewState.resumeCamera(false)
            viewState.displayBarCode(result.text)
        }
    }
}
