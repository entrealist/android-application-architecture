package com.rosberry.navigation.di.profile

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * @author mmikhailov on 27.09.2018.
 */
@Module
internal class NavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @ProfileScope
    @Provides
    @ProfileNavigationQualifier
    internal fun provideRouter() = cicerone.router

    @ProfileScope
    @Provides
    @ProfileNavigationQualifier
    internal fun provideNavigatorHolder() = cicerone.navigatorHolder
}