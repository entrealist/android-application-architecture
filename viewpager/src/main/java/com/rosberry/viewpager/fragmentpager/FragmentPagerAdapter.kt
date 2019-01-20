package com.rosberry.viewpager.fragmentpager

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan
import android.view.ViewGroup
import com.rosberry.viewpager.R
import com.rosberry.viewpager.fragmentpager.tabs.Tab1Fragment
import com.rosberry.viewpager.fragmentpager.tabs.Tab2Fragment
import com.rosberry.viewpager.fragmentpager.tabs.Tab3Fragment

/**
 * @author mmikhailov on 12.10.2018.
 */
class FragmentPagerAdapter(
        fm: FragmentManager,
        private val context: Context
) : FragmentStatePagerAdapter(fm) {

    companion object {
        const val POSITION_TAB_1 = 0
        const val POSITION_TAB_2 = 1
        const val POSITION_TAB_3 = 2

        const val IMAGE_RES_TAB = R.drawable.ic_android_black_24dp

        const val TAB_COUNT = 3
    }
    
    private var tab1Fragment: Tab1Fragment? = null
    private var tab2Fragment: Tab2Fragment? = null
    private var tab3Fragment: Tab3Fragment? = null

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            POSITION_TAB_1 -> Tab1Fragment()
            POSITION_TAB_2 -> Tab2Fragment()
            POSITION_TAB_3 -> Tab3Fragment()
            else -> null
        }
    }

    override fun getCount(): Int {
        return TAB_COUNT
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val createdFragment = super.instantiateItem(container, position) as Fragment
        when (position) {
            POSITION_TAB_1 -> tab1Fragment = createdFragment as Tab1Fragment
            POSITION_TAB_2 -> tab2Fragment = createdFragment as Tab2Fragment
            POSITION_TAB_3 -> tab3Fragment = createdFragment as Tab3Fragment
        }
        return createdFragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        val pageTabTitleRes = when (position) {
            POSITION_TAB_1 -> R.string.tab1
            POSITION_TAB_2 -> R.string.tab2
            POSITION_TAB_3 -> R.string.tab3
            else -> 0
        }

        val pageTabTitle = context.getString(pageTabTitleRes)
        val pageTabDrawable = ContextCompat.getDrawable(context, IMAGE_RES_TAB)

        return if (pageTabDrawable != null) {
            pageTabDrawable.setBounds(0, 0, pageTabDrawable.intrinsicWidth, pageTabDrawable.intrinsicHeight)

            SpannableString("   $pageTabTitle")
                .apply {
                    val imageSpan = ImageSpan(pageTabDrawable, ImageSpan.ALIGN_BOTTOM)
                    setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
        } else {
            pageTabTitle
        }
    }
}