package com.rosberry.android.sample.di.main

import com.rosberry.android.sample.presentation.main.MainPresenter
import dagger.Subcomponent

/**
 * Created by neestell on 07/09/2018.
 */
@Subcomponent
interface MainComponent {

    fun getMainPresenter(): MainPresenter

}