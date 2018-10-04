package com.rosberry.navigation.presentation.profile

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.R
import com.rosberry.navigation.Screens
import com.rosberry.navigation.di.app.GlobalNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import com.rosberry.navigation.ui.profile.ProfileContainerView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 27.09.2018.
 */
@InjectViewState
class ProfileContainerPresenter @Inject constructor(
        @GlobalNavigationQualifier private val router: Router
) : BasePresenter<ProfileContainerView>() {

    private val pageTabModelList = ArrayList<PageTabModel>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        createPageTabModels()
        viewState.initPagerAdapter(pageTabModelList)
    }

    fun pressBack() {
        router.exit()
    }

    private fun createPageTabModels() {
        pageTabModelList.add(PageTabModel(
                Screens.ProfileDetailsScreen(),
                R.string.profile_page_details_title,
                R.drawable.ic_android_black_24dp
        ))
        pageTabModelList.add(PageTabModel(
                Screens.ProfileNotificationsScreen(),
                R.string.profile_page_notif_title,
                R.drawable.ic_android_black_24dp
        ))
        pageTabModelList.add(PageTabModel(
                Screens.ProfilePrivacyScreen(),
                R.string.profile_page_privacy_title,
                R.drawable.ic_android_black_24dp
        ))
    }
}
