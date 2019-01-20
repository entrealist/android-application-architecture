package com.rosberry.barcodescanner.scanner

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.zxing.Result

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
