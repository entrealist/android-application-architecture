package com.rosberry.navigation.ui.items

import android.content.Context
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.di.items.ItemsNavigationQualifier
import com.rosberry.navigation.presentation.items.ItemsContainerPresenter
import com.rosberry.navigation.presentation.items.ItemsContainerView
import com.rosberry.navigation.ui.base.BaseFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

/**
 * @author mmikhailov on 30.09.2018.
 */
class ItemsContainerFragment : BaseFragment(), ItemsContainerView {

    companion object {
        fun newInstance(): ItemsContainerFragment {
            return ItemsContainerFragment()
        }
    }

    override val layoutRes = R.layout.f_items_container

    private val navigator by lazy { SupportAppNavigator(activity, childFragmentManager, R.id.fragmentContainer) }

    @Inject
    @field:ItemsNavigationQualifier
    lateinit var navigatorHolder: NavigatorHolder

    @InjectPresenter
    lateinit var presenter: ItemsContainerPresenter

    @ProvidePresenter
    fun providePresenter(): ItemsContainerPresenter {
        return AndroidInjector
            .openItemsScope()
            .providePresenter()
    }

    override fun onAttach(context: Context?) {
        AndroidInjector
            .openItemsScope()
            .inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onBackPressed(): Boolean {
        presenter.pressBack()
        return true
    }
}