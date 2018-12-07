package com.rosberry.navigation.presentation.main.navigation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import ru.terrakok.cicerone.commands.Command

/**
 * @author mmikhailov on 07/12/2018.
 */
class SupportTabsNavigator(
        private val fragmentManager: FragmentManager,
        private val containerId: Int
) : Navigator {

    override fun applyCommands(commands: Array<out Command>) {
        fragmentManager.executePendingTransactions()

        for (command in commands) {
            applyCommand(command)
        }
    }

    /**
     * Perform transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    private fun applyCommand(command: Command) {
        if (command is OpenTab) {
            openTab(command)
        }
    }

    private fun openTab(command: OpenTab) {
        val screen = command.screen as SupportAppScreen
        var currentFragment: Fragment? = null
        for (f in fragmentManager.fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }
        val newFragment = fragmentManager.findFragmentByTag(screen.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return

        val transaction = fragmentManager.beginTransaction()
        if (newFragment == null) {
            transaction.add(containerId, screen.fragment, screen.screenKey)
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        if (newFragment != null) {
            transaction.show(newFragment)
        }

        transaction.commitNow()
    }
}