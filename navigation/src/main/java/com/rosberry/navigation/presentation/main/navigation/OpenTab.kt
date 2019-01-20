package com.rosberry.navigation.presentation.main.navigation

import ru.terrakok.cicerone.Screen
import ru.terrakok.cicerone.commands.Command

/**
 * @author mmikhailov on 07/12/2018.
 */
data class OpenTab(val screen: Screen) : Command