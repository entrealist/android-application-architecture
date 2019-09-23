package com.rosberry.navigation.ui.login

import android.os.Bundle
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.presentation.login.LoginPresenter
import com.rosberry.navigation.presentation.login.LoginView
import com.rosberry.navigation.ui.base.BaseActivity
import kotlinx.android.synthetic.main.a_login.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

/**
 * @author mmikhailov on 01.10.2018.
 */
class LoginActivity : BaseActivity(), LoginView {

    override val layoutRes = R.layout.a_login

    @Inject
    @field:GlobalNavigationQualifier
    lateinit var globalNavigatorHolder: NavigatorHolder

    private var globalNavigator = SupportAppNavigator(this, -1)

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return AndroidInjector.openLoginScope()
            .providePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjector.openLoginScope()
            .inject(this)
        super.onCreate(savedInstanceState)

        toOnBoardingBtn.setOnClickListener { presenter.clickLogIn(true) }
        toBottomNavigationBtn.setOnClickListener { presenter.clickLogIn(false) }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        globalNavigatorHolder.setNavigator(globalNavigator)
    }

    override fun onPause() {
        globalNavigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            AndroidInjector.closeLoginScope()
        }
    }

    override fun handleBackPress(): Boolean {
        presenter.pressBack()
        return true
    }
}
