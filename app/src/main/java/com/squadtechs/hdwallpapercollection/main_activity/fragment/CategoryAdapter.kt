package com.squadtechs.hdwallpapercollection.main_activity.fragment

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squadtechs.hdwallpapercollection.R

class CategoryAdapter {
    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val frame: FrameLayout = view.findViewById(R.id.grid_frame)
        val imgGrid: ImageView = view.findViewById(R.id.img_grid)
        val txtCategory: TextView = view.findViewById(R.id.txt_categories)
        val touchView: View = view.findViewById(R.id.grid_touch_view)
    }
}