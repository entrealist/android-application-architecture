package com.rosberry.navigation.di.main

import com.rosberry.navigation.presentation.main.navigation.TabsRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone

/**
 * @author mmikhailov on 30.09.2018.
 */
@Module
internal class NavigationModule {

    private val cicerone = Cicerone.create(TabsRouter())

    @Provides
    @TabsNavigationQualifier
    internal fun provideRouter() = cicerone.router

    @Provides
    @TabsNavigationQualifier
    internal fun provideNavigatorHolder() = cicerone.navigatorHolder
}