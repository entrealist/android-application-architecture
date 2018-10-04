package com.rosberry.navigation.di.login

import com.rosberry.navigation.presentation.login.LoginPresenter
import com.rosberry.navigation.ui.login.LoginActivity
import dagger.Subcomponent

/**
 * @author mmikhailov on 01.10.2018.
 */
@LoginScope
@Subcomponent()
interface LoginComponent {

    fun providePresenter(): LoginPresenter
    fun inject(activity: LoginActivity)
}