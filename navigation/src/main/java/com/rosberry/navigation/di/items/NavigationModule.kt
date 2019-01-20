package com.rosberry.navigation.di.items

import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

/**
 * @author mmikhailov on 30.09.2018.
 */
@Module
internal class NavigationModule {

    private val cicerone: Cicerone<Router> = Cicerone.create()

    @ItemsScope
    @Provides
    @ItemsNavigationQualifier
    internal fun provideRouter() = cicerone.router

    @ItemsScope
    @Provides
    @ItemsNavigationQualifier
    internal fun provideNavigatorHolder() = cicerone.navigatorHolder
}