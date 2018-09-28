package com.rosberry.android.sample.di.data

import com.rosberry.android.sample.di.data.qualifier.BaseUrl
import com.rosberry.android.sample.di.main.MainComponent
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author neestell on 7/09/2018.
 */
@DataScope
@Subcomponent(modules = [(NetworkModule::class), (DeviceInfoModule::class)])
interface DataComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun setBaseUrl(@BaseUrl url: String): Builder

        fun build(): DataComponent
    }

    fun plusMainComponent(): MainComponent

}