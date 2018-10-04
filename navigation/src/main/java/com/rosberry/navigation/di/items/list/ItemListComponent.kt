package com.rosberry.navigation.di.items.list

import com.rosberry.navigation.presentation.items.list.ItemListPresenter
import dagger.Subcomponent

/**
 * @author mmikhailov on 27.09.2018.
 */
@Subcomponent
interface ItemListComponent {

    fun providePresenter(): ItemListPresenter
}