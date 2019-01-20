package com.rosberry.navigation.presentation.main.navigation

import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.Screen

/**
 * @author mmikhailov on 07/12/2018.
 */
class TabsRouter : Router() {

    fun openTab(screen: Screen) {
        executeCommands(OpenTab(screen))
    }
}