package com.squadtechs.hdwallpapercollection.activity_view_wallpapers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.squadtechs.hdwallpapercollection.main_activity.fragment.WallpaperModel

class MyPagerAdapter(private val list: ArrayList<WallpaperModel>, fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        val fragment = FragmentViewWallpaper()
        val bundle = Bundle()
        bundle.putString("url", list[position].wallpaper_image_url)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int = list.size
}