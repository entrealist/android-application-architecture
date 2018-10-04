package com.rosberry.navigation.presentation.about.content

import com.arellomobile.mvp.InjectViewState
import com.rosberry.navigation.di.about.AboutNavigationQualifier
import com.rosberry.navigation.presentation.base.BasePresenter
import com.rosberry.navigation.ui.about.AboutItem
import ru.terrakok.cicerone.Router
import javax.inject.Inject

/**
 * @author mmikhailov on 30.09.2018.
 */
@InjectViewState
class AboutContentPresenter @Inject constructor(
        @AboutNavigationQualifier private val aboutRouter: Router
) : BasePresenter<AboutContentView>() {

    private val aboutItemList = ArrayList<AboutItem>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        createPageTabModels()
        viewState.setAboutItems(aboutItemList)
    }

    private fun createPageTabModels() {
        for (i in 0..5) {
            aboutItemList.add(AboutItem(i.toString()))
        }
    }
}