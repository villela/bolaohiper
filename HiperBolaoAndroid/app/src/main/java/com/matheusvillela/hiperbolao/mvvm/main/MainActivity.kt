package com.matheusvillela.hiperbolao.mvvm.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.matheusvillela.hiperbolao.R
import com.matheusvillela.hiperbolao.R.id.navigation_home
import com.matheusvillela.hiperbolao.mvvm.games.GamesFragment
import com.matheusvillela.hiperbolao.mvvm.standings.StandingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(activity_main_toolbar)

        activity_main_pager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment = if (position == 0) StandingsFragment() else GamesFragment()
            override fun getCount(): Int = 2
        }

        activity_main_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                activity_main_navigation.menu.getItem(position).isChecked = true
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        activity_main_navigation.setOnNavigationItemSelectedListener {
            activity_main_pager.currentItem = when (it.itemId) {
                navigation_home -> 0
                else -> 1
            }
            true
        }
    }
}
