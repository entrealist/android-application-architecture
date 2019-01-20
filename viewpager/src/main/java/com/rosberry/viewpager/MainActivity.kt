package com.rosberry.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.rosberry.viewpager.fragmentpager.FragmentPagerFragment
import com.rosberry.viewpager.itempager.ItemPagerFragment


class MainActivity : AppCompatActivity() {

    private val itemsFragment = ItemPagerFragment()
    private val fragmentsFragment = FragmentPagerFragment()

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
                openFragment(itemsFragment); true
            }
            R.id.op_fragments -> {
                openFragment(fragmentsFragment); true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openFragment(f: Fragment) {
            supportFragmentManager.beginTransaction()
                .apply {
                    replace(R.id.fragmentContainer, f)
                    addToBackStack(null)
                    commit()
                }
    }
}
