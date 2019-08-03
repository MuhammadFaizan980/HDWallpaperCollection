package com.squadtechs.hdwallpapercollection.main_activity.PagerAdapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.squadtechs.hdwallpapercollection.main_activity.fragment.FragmentMain

class CustomPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val fragment = FragmentMain()
        val bundle = Bundle()
        bundle.putInt("position", position)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return 2
    }
}