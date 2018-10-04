package com.rosberry.navigation.presentation.profile

import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author mmikhailov on 02.10.2018.
 */
data class PageTabModel(
        val screen: SupportAppScreen,
        val tabTitleRes: Int,
        val tabIconRes: Int
)