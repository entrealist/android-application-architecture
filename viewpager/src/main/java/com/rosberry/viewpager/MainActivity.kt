package com.rosberry.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.rosberry.viewpager.fragmentpager.FragmentPagerFragment
import com.rosberry.viewpager.itempager.ItemPagerFragment


class MainActivity : AppCompatActivity() {

    private val itemsFragmentTag = "itemsFragmentTag"
    private val fragmentsFragmentTag = "fragmentsFragmentTag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.m_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.op_items -> {
                openFragment(itemsFragmentTag); true
            }
            R.id.op_fragments -> {
                openFragment(fragmentsFragmentTag); true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(tag: String) {
        var currentFragment: Fragment? = null
        for (f in supportFragmentManager.fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }

        val newFragment = supportFragmentManager.findFragmentByTag(tag)

        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return

        val transaction = supportFragmentManager.beginTransaction()
        if (newFragment == null) {
            val f = when (tag) {
                itemsFragmentTag -> ItemPagerFragment()
                fragmentsFragmentTag -> FragmentPagerFragment()
                else -> ItemPagerFragment()
            }
            transaction.add(R.id.fragmentContainer, f, tag)
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
