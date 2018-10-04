package com.rosberry.navigation.di

import android.content.Context
import com.rosberry.navigation.di.about.AboutContainerComponent
import com.rosberry.navigation.di.app.ApplicationComponent
import com.rosberry.navigation.di.app.DaggerApplicationComponent
import com.rosberry.navigation.di.data.DataComponent
import com.rosberry.navigation.di.items.ItemsContainerComponent
import com.rosberry.navigation.di.items.details.ItemDetailsComponent
import com.rosberry.navigation.di.launch.LaunchComponent
import com.rosberry.navigation.di.login.LoginComponent
import com.rosberry.navigation.di.main.BottomNavigationComponent
import com.rosberry.navigation.di.onboarding.OnBoardingComponent
import com.rosberry.navigation.di.profile.ProfileContainerComponent
import com.rosberry.navigation.di.settings.SettingsComponent

object AndroidInjector {

    private lateinit var appComponent: ApplicationComponent
    private var dataComponent: DataComponent? = null
    private var launchComponent: LaunchComponent? = null
    private var onBoardingComponent: OnBoardingComponent? = null
    private var loginComponent: LoginComponent? = null
    private var bottomNavigationComponent: BottomNavigationComponent? = null
    private var profileContainerComponent: ProfileContainerComponent? = null
    private var itemsContainerComponent: ItemsContainerComponent? = null
    private var aboutContainerComponent: AboutContainerComponent? = null
    private var settingsComponent: SettingsComponent? = null

    fun openAppScope(context: Context): ApplicationComponent {
        if (!AndroidInjector::appComponent.isInitialized) {
            appComponent = DaggerApplicationComponent.builder()
                .application(context)
                .build()
        }
        return appComponent
    }

    fun openDataScope(): DataComponent {
        if (dataComponent == null) {
            dataComponent = appComponent.plusDataComponent()
                .setBaseUrl("BASE_URL")
                .build()
        }

        return dataComponent!!
    }

    fun openLaunchScope(): LaunchComponent {
        if (launchComponent == null) {
            launchComponent = openDataScope().plusLaunchComponent()
        }

        return launchComponent!!
    }

    fun closeLaunchScope() {
        launchComponent = null
    }

    fun openOnBoardingScope(): OnBoardingComponent {
        if (onBoardingComponent == null) {
            onBoardingComponent = openDataScope().plusOnBoardingComponent()
        }

        return onBoardingComponent!!
    }

    fun closeOnBoardingScope() {
        onBoardingComponent = null
    }

    fun openLoginScope(): LoginComponent {
        if (loginComponent == null) {
            loginComponent = openDataScope().plusLoginComponent()
        }

        return loginComponent!!
    }

    fun closeLoginScope() {
        loginComponent = null
    }

    fun openBottomNavigationScope(): BottomNavigationComponent {
        if (bottomNavigationComponent == null) {
            bottomNavigationComponent = openDataScope().plusBottomNavigationComponent()
        }
        return bottomNavigationComponent!!
    }

    fun closeBottomNavigationScope() {
        bottomNavigationComponent = null
        profileContainerComponent = null
        itemsContainerComponent = null
        aboutContainerComponent = null
    }

    fun openProfileScope(): ProfileContainerComponent {
        if (profileContainerComponent == null) {
            profileContainerComponent = openBottomNavigationScope().plusProfileComponent()
        }

        return profileContainerComponent!!
    }

    fun openItemsScope(): ItemsContainerComponent {
        if (itemsContainerComponent == null) {
            itemsContainerComponent = openBottomNavigationScope().plusItemsComponent()
        }

        return itemsContainerComponent!!
    }

    fun openItemDetailsScope(id: String): ItemDetailsComponent {
        return openItemsScope().plusItemDetailsComponent()
            .itemId(id)
            .build()
    }

    fun openAboutScope(): AboutContainerComponent {
        if (aboutContainerComponent == null) {
            aboutContainerComponent = openBottomNavigationScope().plusAboutComponent()
        }

        return aboutContainerComponent!!
    }

    fun openSettingsScope(): SettingsComponent {
        if (settingsComponent == null) {
            settingsComponent = openBottomNavigationScope().plusSettingsComponent()
        }

        return settingsComponent!!
    }

    fun closeSettingsScope() {
        settingsComponent = null
    }
}