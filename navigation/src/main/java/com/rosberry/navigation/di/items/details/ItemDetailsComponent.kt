package com.rosberry.navigation.di.items.details

import com.rosberry.navigation.presentation.items.details.ItemDetailsPresenter
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author mmikhailov on 30.09.2018.
 */
@Subcomponent
interface ItemDetailsComponent {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun itemId(@ItemsItemIdQualifier id: String): Builder

        fun build(): ItemDetailsComponent

    }

    fun providePresenter(): ItemDetailsPresenter
}