package com.squadtechs.hdwallpapercollection.main_activity

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
    private lateinit var txtCategory: TextView
    private lateinit var txtNew: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkConnection()) {
            initViews()
            prepareToolbar()
            prepareNavigationView()
            prepareViewPager()
            checkPerm()
        } else {
            showNetworkErrorDialog()
        }
    }

    private fun showNetworkErrorDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Warning!")
        alertDialog.setMessage("Active internet connection is required\nTurn on your wifi or packet data")
        alertDialog.setCancelable(false)
        alertDialog.setPositiveButton("Close") { dialogInterface, i ->
            finish()
        }
        alertDialog.show()
    }

    private fun checkConnection(): Boolean {
        val connectivityManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun prepareViewPager() {
        viewPager.adapter = pagerAdapter
        txtCategory.textSize = 18f
        txtCategory.setBackgroundColor(Color.parseColor("#FF2C2C2C"))
        txtNew.setTextColor(Color.parseColor("#FFC9C9C9"))
        txtCategory.setTextColor(Color.parseColor("#FFFFFF"))
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    txtCategory.textSize = 18f
                    txtNew.textSize = 14f
                    txtNew.setTextColor(Color.parseColor("#FFC9C9C9"))
                    txtCategory.setTextColor(Color.parseColor("#FFFFFF"))
                    txtCategory.setBackgroundColor(Color.parseColor("#FF2C2C2C"))
                    txtNew.setBackgroundColor(Color.parseColor("#424242"))
                } else {
                    txtNew.textSize = 18f
                    txtCategory.textSize = 14f
                    txtNew.setTextColor(Color.parseColor("#FFFFFF"))
                    txtCategory.setTextColor(Color.parseColor("#FFC9C9C9"))
                    txtNew.setBackgroundColor(Color.parseColor("#FF2C2C2C"))
                    txtCategory.setBackgroundColor(Color.parseColor("#424242"))
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
        txtCategory = findViewById(R.id.txt_categories)
        txtNew = findViewById(R.id.txt_new)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
        return true
    }

    public fun txtCategoryListener(view: View) {
        viewPager.currentItem = 0
        this.txtCategory.textSize = 18f
        txtNew.textSize = 14f
        txtNew.setTextColor(Color.parseColor("#FFC9C9C9"))
        txtCategory.setTextColor(Color.parseColor("#FFFFFF"))
        txtCategory.setBackgroundColor(Color.parseColor("#FF2C2C2C"))
        txtNew.setBackgroundColor(Color.parseColor("#424242"))
    }

    public fun txtNewListener(view: View) {
        viewPager.currentItem = 1
        txtNew.textSize = 18f
        txtCategory.textSize = 14f
        txtNew.setTextColor(Color.parseColor("#FFFFFF"))
        txtCategory.setTextColor(Color.parseColor("#FFC9C9C9"))
        txtNew.setBackgroundColor(Color.parseColor("#FF2C2C2C"))
        txtCategory.setBackgroundColor(Color.parseColor("#424242"))
    }

    private fun checkPerm() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
            || ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val arr = arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            ActivityCompat.requestPermissions(this@MainActivity, arr, 65)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 65 && grantResults.isNotEmpty()) {
            for (i in grantResults) {
                if (i == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Storage permission is required for this app", Toast.LENGTH_LONG).show()
                    return
                }
            }
        }
    }

}
