package com.rosberry.navigation.di.data

import com.rosberry.navigation.di.data.qualifier.BaseUrl
import com.rosberry.navigation.di.launch.LaunchComponent
import com.rosberry.navigation.di.login.LoginComponent
import com.rosberry.navigation.di.main.BottomNavigationComponent
import com.rosberry.navigation.di.onboarding.OnBoardingComponent
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author neestell on 7/09/2018.
 */
@DataScope
@Subcomponent(modules = [(DeviceInfoModule::class)])
interface DataComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun setBaseUrl(@BaseUrl url: String): Builder

        fun build(): DataComponent
    }

    fun plusLaunchComponent(): LaunchComponent
    fun plusLoginComponent(): LoginComponent
    fun plusOnBoardingComponent(): OnBoardingComponent
    fun plusBottomNavigationComponent(): BottomNavigationComponent
}