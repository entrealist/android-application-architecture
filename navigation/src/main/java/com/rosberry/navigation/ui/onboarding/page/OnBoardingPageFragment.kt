package com.rosberry.navigation.ui.onboarding.page

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.presentation.onboarding.page.OnBoardingPagePresenter
import com.rosberry.navigation.presentation.onboarding.page.OnBoardingPageView
import com.rosberry.navigation.ui.base.BaseFragment
import com.rosberry.navigation.ui.onboarding.OnBoardingCompleteListener
import kotlinx.android.synthetic.main.f_on_boarding_page.*

/**
 * @author mmikhailov on 01.10.2018.
 */
class OnBoardingPageFragment : BaseFragment(), OnBoardingPageView {

    companion object {
        fun newInstance() = OnBoardingPageFragment()
    }

    override val layoutRes = R.layout.f_on_boarding_page

    @InjectPresenter
    lateinit var presenter: OnBoardingPagePresenter

    @ProvidePresenter
    fun providePresenter(): OnBoardingPagePresenter {
        return AndroidInjector
            .openOnBoardingScope()
            .plusOnBoardingPageComponent()
            .providePresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startBtn.setOnClickListener { presenter.clickOnStart() }
    }

    override fun onBackPressed(): Boolean {
        return presenter.pressBack()
    }

    override fun completeOnBoarding() {
        (activity as OnBoardingCompleteListener).onOnBoardingComplete()
    }
}