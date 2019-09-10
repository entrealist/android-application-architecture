package com.rosberry.navigation.ui.items.details

import android.os.Bundle
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.presentation.items.details.ItemDetailsPresenter
import com.rosberry.navigation.presentation.items.details.ItemDetailsView
import com.rosberry.navigation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.f_item_details.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

/**
 * @author mmikhailov on 30.09.2018.
 */
class ItemDetailsFragment : BaseFragment(), ItemDetailsView {

    companion object {
        private const val EXTRA_ITEM_ID = "EXTRA_ITEM_ID"

        fun newInstance(id: String): ItemDetailsFragment {
            val instance = ItemDetailsFragment()
            instance.arguments = Bundle().apply {
                putString(EXTRA_ITEM_ID, id)
            }
            return instance
        }
    }

    override val layoutRes = R.layout.f_item_details

    @InjectPresenter
    lateinit var presenter: ItemDetailsPresenter

    @ProvidePresenter
    fun providePresenter(): ItemDetailsPresenter {
        return AndroidInjector
            .openItemDetailsScope(arguments?.getString(EXTRA_ITEM_ID, "-1") ?: "-1")
            .providePresenter()
    }

    override fun setItemDetails(id: String) {
        val text = "Item id is $id"
        itemId.text = text
    }

    override fun onBackPressed(): Boolean {
        return (parentFragment as BaseFragment).onBackPressed()
    }
}