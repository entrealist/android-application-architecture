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
import com.rosberry.navigation.presentation.main.BottomNavigationPresenter
import com.rosberry.navigation.presentation.main.BottomNavigationView
import com.rosberry.navigation.ui.base.BaseActivity
import com.rosberry.navigation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.a_bottom_navigation.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

/**
 * @author mmikhailov on 27.09.2018.
 */
class BottomNavigationActivity : BaseActivity(), BottomNavigationView {

    override val layoutRes = R.layout.a_bottom_navigation

    @Inject
    @field:GlobalNavigationQualifier
    lateinit var globalNavigatorHolder: NavigatorHolder

    private var globalNavigator: Navigator = SupportAppNavigator(this, -1)

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
    }

    override fun onPause() {
        globalNavigatorHolder.removeNavigator()
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

    override fun openTab(tabScreen: SupportAppScreen) {
        var currentFragment: Fragment? = null
        for (f in supportFragmentManager.fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }
        val newFragment = supportFragmentManager.findFragmentByTag(tabScreen.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return

        val transaction = supportFragmentManager.beginTransaction()
        if (newFragment == null) {
            transaction.add(R.id.fragmentContainer, tabScreen.fragment, tabScreen.screenKey)
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        if (newFragment != null) {
            transaction.show(newFragment)
        }

        transaction.commitNow()
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
