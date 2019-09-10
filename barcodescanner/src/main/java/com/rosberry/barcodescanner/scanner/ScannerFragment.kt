package com.rosberry.barcodescanner.scanner

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.media.AudioAttributes
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.google.zxing.Result
import com.rosberry.barcodescanner.MainActivity
import com.rosberry.barcodescanner.R
import me.dm7.barcodescanner.zxing.ZXingScannerView
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import permissions.dispatcher.RuntimePermissions


@RuntimePermissions
class ScannerFragment : MvpAppCompatFragment(), ScannerView, ZXingScannerView.ResultHandler {

    companion object {
        const val TAG = "ScannerFragment"
        private const val VIB_DURATION = 125L
    }

    @InjectPresenter
    lateinit var presenter: ScannerPresenter

    private val handler = Handler()
    private var cameraStarted = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.setTitle(R.string.nav_scanner)

        setHasOptionsMenu(true)
        scanner.setResultHandler(this)
    }

    override fun onStart() {
        super.onStart()

        startCameraWithPermissionCheck()
    }

    override fun onStop() {
        handler.removeCallbacksAndMessages(null)
        if (cameraStarted) {
            Log.d(TAG, "Stopping camera")
            cameraStarted = false
            scanner.stopCamera()
        } else {
            Log.d(TAG, "Camera wasn't started")
        }

        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.scanner, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)

        menu?.findItem(R.id.action_torch_on)
            ?.isVisible = !scanner.flash
        menu?.findItem(R.id.action_torch_off)
            ?.isVisible = scanner.flash
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId ?: 0) {
            R.id.action_torch_on -> {
                scanner.flash = true
                requireActivity().invalidateOptionsMenu()

            }
            R.id.action_torch_off -> {
                scanner.flash = false
                requireActivity().invalidateOptionsMenu()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun handleResult(result: Result?) {
        presenter.onBarcodeReady(result)
    }

    override fun resumeCamera(scannerActive: Boolean) {
        scanner.resumeCameraPreview(if (scannerActive) this else null)
        scanner.setLaserEnabled(scannerActive)
    }

    override fun displayBarCode(text: String) {
        Log.d(TAG, "displayBarCode::displaying text: $text")
        (requireActivity() as MainActivity).showResult(text)
    }

    @SuppressLint("NewApi")
    @Suppress("DEPRECATION")
    override fun vibrate() {
        val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .build()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createOneShot(
                            VIB_DURATION, 128), audioAttributes)
                } else {
                    vibrator.vibrate(VIB_DURATION, audioAttributes)
                }
            } else {
                vibrator.vibrate(VIB_DURATION)
            }
        }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun startCamera() {
        handler.post {
            cameraStarted = true
            scanner.startCamera()
        }
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.need_permissions)
            .setMessage(R.string.need_permissions_camera_rationale)
            .setPositiveButton(android.R.string.yes) { _, _ -> request.proceed() }
            .setNegativeButton(android.R.string.no) { _, _ -> request.cancel() }
            .show()
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.need_permissions)
            .setMessage(R.string.need_permissions_camera_to_continue)
            .setPositiveButton(android.R.string.ok) { _, _ -> startCameraWithPermissionCheck() }
            .show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        AlertDialog.Builder(context!!)
            .setTitle(R.string.title_permissions_never)
            .setMessage(R.string.msg_permissions_camera_never)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setCancelable(false)
            .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, grantResults)
    }
}
