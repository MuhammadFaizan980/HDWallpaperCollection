package com.squadtechs.hdwallpapercollection.activity_view_wallpapers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squadtechs.hdwallpapercollection.R

class FragmentViewWallpaper : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragmet_view_wallpaper, container, false)
    }
}
