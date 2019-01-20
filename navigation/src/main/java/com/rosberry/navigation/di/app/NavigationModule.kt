package com.rosberry.navigation.di.app

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import javax.inject.Singleton

/**
 * @author mmikhailov on 27.09.2018.
 */
@Module
internal class NavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @Singleton
    @Provides
    @GlobalNavigationQualifier
    internal fun provideRouter() = cicerone.router

    @Singleton
    @Provides
    @GlobalNavigationQualifier
    internal fun provideNavigatorHolder() = cicerone.navigatorHolder
}