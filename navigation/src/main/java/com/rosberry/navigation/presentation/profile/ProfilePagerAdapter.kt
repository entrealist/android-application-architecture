package com.rosberry.navigation.presentation.profile

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ImageSpan

class ProfilePagerAdapter(
        fm: FragmentManager,
        private val context: Context,
        private val models: ArrayList<PageTabModel>
) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return models[position].screen.fragment
    }

    override fun getCount(): Int {
        return models.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        val pageTabTitle = context.getString(models[position].tabTitleRes)
        val pageTabDrawable = ContextCompat.getDrawable(context, models[position].tabIconRes)

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