package com.rosberry.navigation.ui.launch

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.presentation.launch.LaunchPresenter
import com.rosberry.navigation.presentation.launch.LaunchView
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
class LaunchActivity : MvpAppCompatActivity(), LaunchView {

    @Inject
    @field:GlobalNavigationQualifier
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = SupportAppNavigator(this, -1)

    @InjectPresenter
    lateinit var presenter: LaunchPresenter

    @ProvidePresenter
    fun providePresenter(): LaunchPresenter {
        return AndroidInjector.openLaunchScope()
            .providePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjector.openLaunchScope()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            AndroidInjector.closeLaunchScope()
        }
    }

    override fun onBackPressed() {
        presenter.pressBack()
    }
}