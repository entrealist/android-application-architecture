package com.rosberry.barcodescanner.scanner

import android.content.Context
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF
import android.hardware.Camera
import android.util.AttributeSet
import android.util.Log
import me.dm7.barcodescanner.zxing.ZXingScannerView

class BarcodeScannerView: ZXingScannerView {

    companion object {
        private const val TAG = "BarcodeScannerView"
    }

    private var previewSize: Camera.Size? = null
    private var stopCalled = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)

    override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
        super.onPreviewFrame(data, camera)

        if (camera != null && !stopCalled) {
            val parameters = camera.parameters
            val newSize = parameters.previewSize
            if (newSize != previewSize) {
                previewSize = newSize

                Log.d(TAG, "Preview size changed to ${newSize.width}x${newSize.height}")
                logCameraParameters(parameters)

                if (parameters.maxNumFocusAreas > 0) {
                    val focusRect = calcFocusArea(newSize)
                    Log.d(TAG, "Focus area is $focusRect")
                    parameters.focusAreas = listOf(Camera.Area(focusRect, 1000))
                    camera.parameters = parameters
                }
                else {
                    Log.w(TAG, "Auto-focus is not supported")
                }
            }
        }
    }

    private fun calcFocusArea(previewSize: Camera.Size): Rect {
        val m = Matrix()
        m.setRectToRect(RectF(0f, 0f, previewSize.height.toFloat(), previewSize.width.toFloat()),
                        RectF(-1000f, -1000f, 1000f, 1000f), Matrix.ScaleToFit.FILL)

        val rect = RectF(getFramingRectInPreview(previewSize.width, previewSize.height))
        Log.d(TAG, "Scanner rect is $rect")

        m.mapRect(rect)

        return Rect(rect.left.toInt(), rect.top.toInt(),
                    rect.right.toInt(), rect.bottom.toInt())
    }

    override fun stopCamera() {
        stopCalled = true
        super.stopCamera()
    }

    private fun logCameraParameters(parameters: Camera.Parameters) {
        Log.v(TAG, "maxExposureCompensation = ${parameters.maxExposureCompensation}")
        Log.v(TAG, "maxNumDetectedFaces = ${parameters.maxNumDetectedFaces}")
        Log.v(TAG, "maxNumFocusAreas = ${parameters.maxNumFocusAreas}")
        Log.v(TAG, "maxNumMeteringAreas = ${parameters.maxNumMeteringAreas}")
        Log.v(TAG, "supportedAntibanding = ${parameters.supportedAntibanding}")
        Log.v(TAG, "supportedColorEffects = ${parameters.supportedColorEffects}")
        Log.v(TAG, "supportedFlashModes = ${parameters.supportedFlashModes}")
        Log.v(TAG, "supportedFocusModes = ${parameters.supportedFocusModes}")
        Log.v(TAG, "supportedJpegThumbnailSizes = ${parameters.supportedJpegThumbnailSizes}")
        Log.v(TAG, "supportedPictureFormats = ${parameters.supportedPictureFormats}")
        Log.v(TAG, "supportedPictureSizes = ${parameters.supportedPictureSizes}")
        Log.v(TAG, "supportedPreviewFormats = ${parameters.supportedPreviewFormats}")
        Log.v(TAG, "supportedPreviewFpsRange = ${parameters.supportedPreviewFpsRange}")
        Log.v(TAG, "supportedPreviewFrameRates = ${parameters.supportedPreviewFrameRates}")
        Log.v(TAG, "supportedPreviewSizes = ${parameters.supportedPreviewSizes}")
        Log.v(TAG, "supportedSceneModes = ${parameters.supportedSceneModes}")
        Log.v(TAG, "supportedVideoSizes = ${parameters.supportedVideoSizes}")
        Log.v(TAG, "supportedWhiteBalance = ${parameters.supportedWhiteBalance}")
        Log.v(TAG, "isAutoExposureLockSupported = ${parameters.isAutoExposureLockSupported}")
        Log.v(TAG, "isAutoWhiteBalanceLockSupported = ${parameters.isAutoWhiteBalanceLockSupported}")
        Log.v(TAG, "isSmoothZoomSupported = ${parameters.isSmoothZoomSupported}")
        Log.v(TAG, "isVideoSnapshotSupported = ${parameters.isVideoSnapshotSupported}")
        Log.v(TAG, "isVideoStabilizationSupported = ${parameters.isVideoStabilizationSupported}")
        Log.v(TAG, "isZoomSupported = ${parameters.isZoomSupported}")
    }
}