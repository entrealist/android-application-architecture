package com.rosberry.navigation

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.rosberry.navigation.ui.about.AboutContainerFragment
import com.rosberry.navigation.ui.about.AboutContentFragment
import com.rosberry.navigation.ui.items.ItemsContainerFragment
import com.rosberry.navigation.ui.items.details.ItemDetailsFragment
import com.rosberry.navigation.ui.items.list.ItemListFragment
import com.rosberry.navigation.ui.launch.LaunchActivity
import com.rosberry.navigation.ui.login.LoginActivity
import com.rosberry.navigation.ui.main.BottomNavigationActivity
import com.rosberry.navigation.ui.onboarding.OnBoardingActivity
import com.rosberry.navigation.ui.onboarding.page.OnBoardingPageFragment
import com.rosberry.navigation.ui.profile.ProfileContainerFragment
import com.rosberry.navigation.ui.profile.details.ProfileDetailsFragment
import com.rosberry.navigation.ui.profile.notifications.ProfileNotificationsFragment
import com.rosberry.navigation.ui.profile.privacy.ProfilePrivacyFragment
import com.rosberry.navigation.ui.settings.SettingsActivity
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * @author mmikhailov on 28.09.2018.
 */
class Screens {

    class LaunchScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, LaunchActivity::class.java)
        }
    }

    class LoginScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    class OnBoardingScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, OnBoardingActivity::class.java)
        }
    }

    class OnBoardingPage : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return OnBoardingPageFragment.newInstance()
        }
    }

    class BottomNavigationScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, BottomNavigationActivity::class.java)
        }
    }

    class AboutContainerScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AboutContainerFragment.newInstance()
        }
    }

    class AboutContentScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AboutContentFragment.newInstance()
        }
    }

    class ItemsContainerScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ItemsContainerFragment.newInstance()
        }
    }

    class ItemListScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ItemListFragment.newInstance()
        }
    }

    class ItemDetailsScreen(private val itemId: String) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ItemDetailsFragment.newInstance(itemId)
        }
    }

    class ProfileContainerScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ProfileContainerFragment.newInstance()
        }
    }

    class ProfileDetailsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ProfileDetailsFragment.newInstance()
        }
    }

    class ProfilePrivacyScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ProfilePrivacyFragment.newInstance()
        }
    }

    class ProfileNotificationsScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ProfileNotificationsFragment.newInstance()
        }
    }

    class SettingsScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }
}
