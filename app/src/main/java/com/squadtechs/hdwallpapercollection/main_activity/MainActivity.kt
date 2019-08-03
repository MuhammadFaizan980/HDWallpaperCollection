package com.squadtechs.hdwallpapercollection.main_activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.navigation.NavigationView
import com.squadtechs.hdwallpapercollection.R
import com.squadtechs.hdwallpapercollection.main_activity.PagerAdapter.CustomPagerAdapter

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var navigationView: NavigationView
    private lateinit var drawer: DrawerLayout
    private lateinit var viewPager: ViewPager
    private lateinit var pagerAdapter: CustomPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        prepareToolbar()
        prepareNavigationView()
        prepareViewPager()
    }

    private fun prepareViewPager() {
        viewPager.adapter = pagerAdapter
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    Log.i("dxdiag", "categories")
                } else {
                    Log.i("dxdiag", "new")
                }
            }
        })
    }

    private fun prepareNavigationView() {
        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
    }

    private fun prepareToolbar() {
        toolbar.title = "Wallpapers"
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.navigation_view)
        drawer = findViewById(R.id.main_drawer)
        viewPager = findViewById(R.id.view_pager)
        pagerAdapter = CustomPagerAdapter(supportFragmentManager)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
        return true
    }

}
