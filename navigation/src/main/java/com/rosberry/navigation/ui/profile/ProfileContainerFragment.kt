package com.rosberry.navigation.ui.profile

import android.content.Context
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.rosberry.navigation.R
import com.rosberry.navigation.di.AndroidInjector
import com.rosberry.navigation.presentation.profile.PageTabModel
import com.rosberry.navigation.presentation.profile.ProfileContainerPresenter
import com.rosberry.navigation.presentation.profile.ProfilePagerAdapter
import com.rosberry.navigation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.f_profile_container.*


/**
 * @author mmikhailov on 27.09.2018.
 */
class ProfileContainerFragment : BaseFragment(), ProfileContainerView {

    companion object {
        fun newInstance(): ProfileContainerFragment {
            return ProfileContainerFragment()
        }
    }

    override val layoutRes = R.layout.f_profile_container

    @InjectPresenter
    lateinit var presenter: ProfileContainerPresenter

    @ProvidePresenter
    fun providePresenter(): ProfileContainerPresenter {
        return AndroidInjector
            .openProfileScope()
            .providePresenter()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidInjector
            .openProfileScope()
            .inject(this)
    }

    override fun onBackPressed(): Boolean {
        presenter.pressBack()
        return true
    }

    override fun initPagerAdapter(models: ArrayList<PageTabModel>) {
        viewPager.adapter = ProfilePagerAdapter(childFragmentManager, requireContext(), models)
        tabLayout.setupWithViewPager(viewPager)
    }
}