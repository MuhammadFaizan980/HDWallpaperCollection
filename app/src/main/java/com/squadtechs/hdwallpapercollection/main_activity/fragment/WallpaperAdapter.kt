package com.squadtechs.hdwallpapercollection.main_activity.fragment

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.squadtechs.hdwallpapercollection.R
import com.squareup.picasso.Picasso


class WallpaperAdapter(val context: Context, val activity: Activity, val list: ArrayList<WallpaperModel>) :
    RecyclerView.Adapter<WallpaperAdapter.WallpaperHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperHolder =
        WallpaperHolder(LayoutInflater.from(context).inflate(R.layout.grid_cell, parent, false))

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: WallpaperHolder, position: Int) {
        adjustScreen(holder, position)
        populateViews(holder, position)
        setListener(holder, position)
    }

    private fun setListener(holder: WallpaperHolder, position: Int) {
        holder.touchView.setOnClickListener {
            Toast.makeText(context, "Touched", Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateViews(holder: WallpaperHolder, position: Int) {
        Picasso.get().load(list[position].wallpaper_image_url).into(holder.imgGrid)
        holder.txtCategory.visibility = View.GONE
    }

    private fun adjustScreen(holder: WallpaperHolder, position: Int) {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        holder.frame.layoutParams = FrameLayout.LayoutParams((width / 2), ((25 * height) / 100))
        if (position % 2 == 0) {
            holder.frame.setPadding(32, 16, 16, 16)
        } else {
            holder.frame.setPadding(16, 16, 32, 16)
        }
    }

    inner class WallpaperHolder(view: View) : RecyclerView.ViewHolder(view) {
        val frame: FrameLayout = view.findViewById(R.id.grid_frame)
        val imgGrid: RoundedImageView = view.findViewById(R.id.img_grid)
        val txtCategory: TextView = view.findViewById(R.id.txt_grid_cell)
        val touchView: View = view.findViewById(R.id.grid_touch_view)
    }
}