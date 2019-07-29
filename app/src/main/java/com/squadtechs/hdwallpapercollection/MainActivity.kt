package com.squadtechs.hdwallpapercollection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        prepareToolbar()
    }

    private fun prepareToolbar() {
        toolbar.title = "Wallpapers"
    }

    private fun initViews() {
        toolbar = findViewById(R.id.toolbar)
    }

}
