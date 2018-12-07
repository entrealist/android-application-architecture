package com.rosberry.navigation.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.di.main.TabsNavigationQualifier
import com.rosberry.navigation.presentation.main.BottomNavigationPresenter
import com.rosberry.navigation.presentation.main.BottomNavigationView
import com.rosberry.navigation.presentation.main.navigation.SupportTabsNavigator
import com.rosberry.navigation.ui.base.BaseActivity
import com.rosberry.navigation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.a_bottom_navigation.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

/**
 * @author mmikhailov on 27.09.2018.
 */
class BottomNavigationActivity : BaseActivity(), BottomNavigationView {

    override val layoutRes = R.layout.a_bottom_navigation

    @Inject
    @field:GlobalNavigationQualifier
    lateinit var globalNavigatorHolder: NavigatorHolder

    private var globalNavigator = SupportAppNavigator(this, -1)

    @Inject
    @field:TabsNavigationQualifier
    lateinit var tabsNavigatorHolder: NavigatorHolder

    private val tabsNavigator by lazy { SupportTabsNavigator(supportFragmentManager, R.id.fragmentContainer) }

    @InjectPresenter
    lateinit var presenter: BottomNavigationPresenter

    @ProvidePresenter
    fun providePresenter(): BottomNavigationPresenter {
        return AndroidInjector.openBottomNavigationScope()
            .providePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjector.openBottomNavigationScope()
            .inject(this)
        super.onCreate(savedInstanceState)

        initBottomNavigationBar()

        if (savedInstanceState == null) {
            bottomNavigationBar.selectTab(0, true)
        }

        settingsImg.setOnClickListener { presenter.clickSettings() }
        logoutImg.setOnClickListener { presenter.clickLogout() }
        backArrowImg.setOnClickListener { onBackPressed() }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        globalNavigatorHolder.setNavigator(globalNavigator)
        tabsNavigatorHolder.setNavigator(tabsNavigator)
    }

    override fun onPause() {
        globalNavigatorHolder.removeNavigator()
        tabsNavigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing) {
            AndroidInjector.closeBottomNavigationScope()
        }
    }

    override fun selectTab(position: Int, shouldCallListener: Boolean) {
        bottomNavigationBar.selectTab(position, shouldCallListener)
    }

    private fun initBottomNavigationBar() {
        bottomNavigationBar
            .addItem(BottomNavigationItem(R.drawable.ic_android_black_24dp, R.string.launch_tab_label_about))
            .addItem(BottomNavigationItem(R.drawable.ic_android_black_24dp, R.string.launch_tab_label_items))
            .addItem(BottomNavigationItem(R.drawable.ic_android_black_24dp, R.string.launch_tab_label_profile))
            .initialise()
        bottomNavigationBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                presenter.clickBottomTab(position)
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
                onTabSelected(position)
            }
        })
    }

    override fun onBackPressed() {
        var fragment: Fragment? = null
        for (f in supportFragmentManager.fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }

        if (fragment != null && fragment is BaseFragment && fragment.onBackPressed()) {
            return
        } else {
            presenter.pressBack()
        }
    }
}
