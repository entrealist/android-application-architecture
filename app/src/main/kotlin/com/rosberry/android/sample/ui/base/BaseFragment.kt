package com.rosberry.android.sample.ui.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.evernote.android.state.State
import com.evernote.android.state.StateSaver
import com.rosberry.android.sample.system.LogUtil
import com.rosberry.android.sample.ui.base.model.DialogFragmentModel

open class BaseFragment : MvpAppCompatDialogFragment() {

    @State var fragmentModel = DialogFragmentModel()
    private var stateSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StateSaver.restoreInstanceState(this, savedInstanceState)

        setStyle(fragmentModel.style, fragmentModel.themeId)
        isCancelable = fragmentModel.isCancelable
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(fragmentModel.layoutId, container, false)

        if (!fragmentModel.isDialog) {
            retainInstance = fragmentModel.retainInstance
            setHasOptionsMenu(fragmentModel.retainInstance && fragmentModel.hasOptionMenu())
        } else {
            with(dialog.window) {
                this?.setBackgroundDrawable(ColorDrawable(0))
                this?.setFlags(
                        dialog.window!!.attributes.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv(),
                        0)
                if (fragmentModel.animationId != 0)  this?.setWindowAnimations(fragmentModel.animationId)

            }
            dialog.setCanceledOnTouchOutside(fragmentModel.isCancelableOutside)

        }

        return view
    }

    override fun onResume() {
        super.onResume()
        stateSaved = false
    }

    override fun onStart() {
        super.onStart()
        LogUtil.d(javaClass, "onStart. fragment: " + this)
    }

    override fun onStop() {
        super.onStop()
        LogUtil.d(javaClass, "onStop. fragment: " + this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        StateSaver.saveInstanceState(this, outState)

        stateSaved = true
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        if (fragmentModel.menuId != -1)
            inflater.inflate(fragmentModel.menuId, menu)
    }

    fun onBackPressed(): Boolean {
        return true
    }

}