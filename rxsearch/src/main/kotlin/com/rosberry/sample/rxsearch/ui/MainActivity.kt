package com.rosberry.sample.rxsearch.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.View
import android.view.ViewPropertyAnimator
import android.widget.Toast
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.sample.rxsearch.R
import com.rosberry.sample.rxsearch.di.AndroidInjector
import com.rosberry.sample.rxsearch.presentation.SearchPresenter
import com.rosberry.sample.rxsearch.presentation.SearchView
import com.rosberry.sample.rxsearch.presentation.model.SearchResultItem

class MainActivity : MvpAppCompatActivity(), SearchView {

    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    fun providePresenter(): SearchPresenter {
        return AndroidInjector.appComponent
            .provideSearchPresenter()
    }

    private val resultAdapter = SearchResultAdapter { id -> presenter.clickResultItem(id) }

    private val focusChangedListener = object : SearchBar.OnFocusChangeListener {
        override fun onFocus() {
            connectLayoutToSearchBar(true)
        }

        override fun onFocusCleared() {
            connectLayoutToSearchBar(false)
        }
    }

    private var placeholderAnimator: ViewPropertyAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultRv.adapter = resultAdapter
        resultRv.setOnTouchListener { _, _ ->
            searchBar.clickOnOutside()
            false
        }

        searchBar.setOnQueryChangeListener { _, newQuery -> presenter.onQuery(newQuery) }
        searchBar.setOnFocusChangeListener(focusChangedListener)
        searchBar.alsoOnLaid { it.setSearchFocused(true) }
    }

    override fun onPause() {
        super.onPause()

        placeholderAnimator?.cancel()
    }

    override fun updateResultItems(items: List<SearchResultItem>) {
        resultAdapter.updateItems(items)
        resultRv.scrollToPosition(0)
    }

    override fun showToast(text: String) {
        Toast.makeText(this, "Click item with id $text", Toast.LENGTH_SHORT)
            .show()
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            searchProgress.visibility = View.VISIBLE
        } else {
            searchProgress.visibility = View.INVISIBLE
        }
    }

    override fun showInteractivePlaceholder(title: String, description: String, actionText: String) {
        interactivePlaceholder.visibility = View.VISIBLE
        titleTxt.text = title
        descriptionTxt.text = description
        interactiveTxt.text = actionText
        interactiveTxt.setOnClickListener { presenter.clickRetry() }
    }

    override fun hideInteractivePlaceholder() {
        interactivePlaceholder.visibility = View.GONE
    }

    private fun connectLayoutToSearchBar(connectToSearchBar: Boolean) {
        // toggle visibility when view is not gone only
        // visibility toggling is needed because of jerking view position while re-constraining and keyboard toggle
        if (interactivePlaceholder.visibility != View.GONE) {
            placeholderAnimator?.cancel()
            placeholderAnimator = interactivePlaceholder?.animate()
                ?.alpha(0.0f)
                ?.setInterpolator(FastOutSlowInInterpolator())
                ?.setDuration(100)
                ?.setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        applyConstraints(connectToSearchBar)
                        placeholderAnimator?.setListener(null)
                        placeholderAnimator = interactivePlaceholder?.animate()
                            ?.alpha(1.0f)
                            ?.setDuration(100)
                    }
                })
        } else {
            applyConstraints(connectToSearchBar)
        }
    }

    private fun applyConstraints(connectToSearchBar: Boolean) {
        searchParentConstraint?.let {
            androidx.constraintlayout.widget.ConstraintSet()
                .apply {
                clone(it)

                if (connectToSearchBar) {
                    connect(R.id.interactivePlaceholder, androidx.constraintlayout.widget.ConstraintSet.TOP, R.id.searchBar, androidx.constraintlayout.widget.ConstraintSet.BOTTOM)
                    connect(R.id.searchProgress, androidx.constraintlayout.widget.ConstraintSet.TOP, R.id.searchBar, androidx.constraintlayout.widget.ConstraintSet.BOTTOM)
                } else {
                    connect(R.id.interactivePlaceholder, androidx.constraintlayout.widget.ConstraintSet.TOP, androidx.constraintlayout.widget.ConstraintSet.PARENT_ID, androidx.constraintlayout.widget.ConstraintSet.TOP)
                    connect(R.id.searchProgress, androidx.constraintlayout.widget.ConstraintSet.TOP, androidx.constraintlayout.widget.ConstraintSet.PARENT_ID, androidx.constraintlayout.widget.ConstraintSet.TOP)
                }

                applyTo(it)
            }
        }
    }
}
