package com.rosberry.navigation.di.about

import com.rosberry.navigation.presentation.about.content.AboutContentPresenter
import dagger.Subcomponent

/**
 * @author mmikhailov on 01.10.2018.
 */
@Subcomponent
interface AboutContentComponent {

    fun providePresenter(): AboutContentPresenter
}