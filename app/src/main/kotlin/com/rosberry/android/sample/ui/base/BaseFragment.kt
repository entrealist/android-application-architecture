package com.rosberry.android.sample.ui.base

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import com.arellomobile.mvp.MvpAppCompatDialogFragment
import com.rosberry.android.sample.system.LogUtil
import com.rosberry.android.sample.ui.base.model.DialogFragmentModel

open class BaseFragment : MvpAppCompatDialogFragment() {

    protected var fragmentModel = DialogFragmentModel()
    private var stateSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(fragmentModel.style, fragmentModel.themeId)
        isCancelable = fragmentModel.isCancelable
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(fragmentModel.layoutId, container, false)
        retainInstance = fragmentModel.retainInstance
        setHasOptionsMenu(fragmentModel.retainInstance && fragmentModel.menuId != -1)

        if (!fragmentModel.isDialog) {
            retainInstance = fragmentModel.retainInstance
            setHasOptionsMenu(fragmentModel.retainInstance && fragmentModel.hasOptionMenu())
        } else {
            with(dialog.window!!) {
                setBackgroundDrawable(ColorDrawable(0))
                setFlags(
                        dialog.window!!.attributes.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv(),
                        0)
                if (fragmentModel.animationId != 0) setWindowAnimations(fragmentModel.animationId)

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