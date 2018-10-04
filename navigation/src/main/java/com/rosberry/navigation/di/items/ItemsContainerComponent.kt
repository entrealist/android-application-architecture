package com.rosberry.navigation.di.items

import com.rosberry.navigation.di.items.details.ItemDetailsComponent
import com.rosberry.navigation.di.items.list.ItemListComponent
import com.rosberry.navigation.presentation.items.ItemsContainerPresenter
import com.rosberry.navigation.ui.items.ItemsContainerFragment
import dagger.Subcomponent

/**
 * @author mmikhailov on 30.09.2018.
 */
@ItemsScope
@Subcomponent(modules = [NavigationModule::class])
interface ItemsContainerComponent {

    fun providePresenter(): ItemsContainerPresenter
    fun inject(fragment: ItemsContainerFragment)

    fun plusItemListComponent(): ItemListComponent
    fun plusItemDetailsComponent(): ItemDetailsComponent.Builder
}