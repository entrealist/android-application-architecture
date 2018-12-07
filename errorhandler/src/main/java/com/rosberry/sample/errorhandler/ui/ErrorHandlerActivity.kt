package com.rosberry.sample.errorhandler.ui

import android.os.Bundle
import com.rosberry.sample.errorhandler.R
import com.rosberry.sample.errorhandler.di.Component
import com.rosberry.sample.errorhandler.entity.State
import com.rosberry.sample.errorhandler.presentation.ErrorPresenter
import com.rosberry.sample.errorhandler.presentation.ErrorView

/**
 * @author Alexei Korshun on 12/10/2018.
 */
class ErrorHandlerActivity : MvpAppCompatActivity(), ErrorView {

    @InjectPresenter
    lateinit var present: ErrorPresenter

    @ProvidePresenter
    fun providePresenter(): ErrorPresenter {
        return Component(applicationContext)
            .providePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.a_error_handler)
        applyButton.setOnClickListener { present.proceed(processSelection(errorGroup.checkedRadioButtonId)) }
        errorGroup.setOnCheckedChangeListener { _, checkedId -> processSelection(checkedId) }
    }

    override fun showError(title: String, description: String) {
        titleView.text = title
        descriptionView.text = description
    }

    override fun showResult(result: String) {
        titleView.text = result
        descriptionView.text = ""
    }

    private fun processSelection(id: Int): State {
        return when (id) {
            R.id.illegalStateExceptionCase -> State.ILLEGAL
            R.id.unknownHostExceptionCase -> State.UNKNOWN_HOST
            R.id.networkErrorExceptionCase -> State.NETWORK_ERROR
            R.id.resultCase -> State.SUCCESS
            else -> State.UNKNOWN
        }
    }
}