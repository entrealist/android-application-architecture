package com.rosberry.navigation.ui.onboarding

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.di.onboarding.OnBoardingNavigationQualifier
import com.rosberry.navigation.presentation.onboarding.OnBoardingPresenter
import com.rosberry.navigation.presentation.onboarding.OnBoardingView
import com.rosberry.navigation.ui.base.BaseActivity
import com.rosberry.navigation.ui.base.BaseFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
class OnBoardingActivity : BaseActivity(), OnBoardingView, OnBoardingCompleteListener {

    override val layoutRes = R.layout.a_on_boarding

    @Inject
    @field:GlobalNavigationQualifier
    lateinit var globalNavigatorHolder: NavigatorHolder
    @Inject
    @field:OnBoardingNavigationQualifier
    lateinit var navigatorHolder: NavigatorHolder

    private var globalNavigator = SupportAppNavigator(this, -1)

    private val navigator by lazy { SupportAppNavigator(this, supportFragmentManager, R.id.fragmentContainer) }

    @InjectPresenter
    lateinit var presenter: OnBoardingPresenter

    @ProvidePresenter
    fun providePresenter(): OnBoardingPresenter {
        return AndroidInjector.openOnBoardingScope()
            .providePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjector.openOnBoardingScope()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        globalNavigatorHolder.setNavigator(globalNavigator)
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        globalNavigatorHolder.removeNavigator()
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            AndroidInjector.closeOnBoardingScope()
        }
    }

    override fun onBackPressed() {
        var fragment: Fragment? = null
        for (f in supportFragmentManager.fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }

        if (fragment != null && fragment is BaseFragment && fragment.onBackPressed()) {
            return
        } else {
            presenter.pressBack()
        }
    }

    override fun onOnBoardingComplete() {
        presenter.completeOnBoarding()
    }
}
