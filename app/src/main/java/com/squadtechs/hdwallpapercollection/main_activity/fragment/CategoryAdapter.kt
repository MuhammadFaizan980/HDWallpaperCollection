package com.squadtechs.hdwallpapercollection.main_activity.fragment

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squadtechs.hdwallpapercollection.R
import com.squareup.picasso.Picasso

class CategoryAdapter(val context: Context, val list: ArrayList<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder =
        CategoryHolder(LayoutInflater.from(context).inflate(R.layout.grid_cell, parent, false))

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int = position

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        adjustScreen(holder, position)
        populateViews(holder, position)
        setListener(holder, position)
    }

    private fun setListener(holder: CategoryHolder, position: Int) {
        holder.touchView.setOnClickListener {
            Toast.makeText(context, "Touched", Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateViews(holder: CategoryHolder, position: Int) {
        Picasso.get().load(list[position].category_image_url).into(holder.imgGrid)
        holder.txtCategory.text = list[position].category_name
    }

    private fun adjustScreen(holder: CategoryHolder, position: Int) {
        val displayMetrics = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        holder.frame.layoutParams = FrameLayout.LayoutParams((width / 2), ((32 * height) / 100))
        if (position % 2 == 0) {
            holder.frame.setPadding(32, 32, 16, 0)
        } else {
            holder.frame.setPadding(16, 32, 32, 0)
        }
    }

    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val frame: FrameLayout = view.findViewById(R.id.grid_frame)
        val imgGrid: ImageView = view.findViewById(R.id.img_grid)
        val txtCategory: TextView = view.findViewById(R.id.txt_categories)
        val touchView: View = view.findViewById(R.id.grid_touch_view)
    }
}