package com.rosberry.navigation.di.onboarding

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * @author mmikhailov on 01.10.2018.
 */
@Module
internal class NavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @OnBoardingScope
    @Provides
    @OnBoardingNavigationQualifier
    internal fun provideRouter() = cicerone.router

    @OnBoardingScope
    @Provides
    @OnBoardingNavigationQualifier
    internal fun provideNavigatorHolder() = cicerone.navigatorHolder
}