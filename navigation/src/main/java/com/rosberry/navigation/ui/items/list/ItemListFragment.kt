package com.rosberry.navigation.ui.items.list

import android.os.Bundle
import android.view.View
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.presentation.items.list.ItemListPresenter
import com.rosberry.navigation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.f_item_list.*
import moxy.MvpView
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

/**
 * @author mmikhailov on 28.09.2018.
 */
class ItemListFragment : BaseFragment(), MvpView {

    companion object {
        fun newInstance() = ItemListFragment()
    }

    override val layoutRes = R.layout.f_item_list

    @InjectPresenter
    lateinit var presenter: ItemListPresenter

    @ProvidePresenter
    fun providePresenter(): ItemListPresenter {
        return AndroidInjector
            .openItemsScope()
            .plusItemListComponent()
            .providePresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toItemDetails.setOnClickListener { presenter.clickOnButton() }
    }

    override fun onBackPressed(): Boolean {
        return (parentFragment as BaseFragment).onBackPressed()
    }
}