package com.squadtechs.hdwallpapercollection.main_activity.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.squadtechs.hdwallpapercollection.R
import com.squadtechs.hdwallpapercollection.activity_wallpapers.ActivityWallpapers
import com.squareup.picasso.Picasso

class CategoryAdapter(val context: Context, val activity: Activity, val list: ArrayList<CategoryModel>) :
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
            context.startActivity(
                Intent(context, ActivityWallpapers::class.java).putExtra(
                    "category_name",
                    list[position].category_name
                ).putExtra("category_key", list[position].category_key).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    }

    private fun populateViews(holder: CategoryHolder, position: Int) {
//        val requestQueue = Volley.newRequestQueue(context)
//        val imageRequest = ImageRequest(
//            list[position].category_image_url,
//            Response.Listener { response ->
//                holder.imgGrid.scaleType = ImageView.ScaleType.CENTER
//                holder.imgGrid.setImageBitmap(response)
//                holder.progress.visibility = View.GONE
//            },
//            1024,
//            860,
//            ImageView.ScaleType.CENTER,
//            null,
//            Response.ErrorListener { error ->
//                holder.progress.visibility = View.GONE
//                Toast.makeText(context, "Error loading Image ${error.networkResponse}", Toast.LENGTH_LONG).show()
//            }).setRetryPolicy(
//            DefaultRetryPolicy(
//                20000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            )
//        )
//        requestQueue.add(imageRequest)
        Picasso.get().load(list[position].category_image_url).resize(1024, 860).into(holder.imgGrid)
        holder.txtCategory.text = list[position].category_name
    }

    private fun adjustScreen(holder: CategoryHolder, position: Int) {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels
        holder.frame.layoutParams = FrameLayout.LayoutParams((width / 2), ((30 * height) / 100))
        if (position % 2 == 0) {
            holder.frame.setPadding(32, 16, 16, 16)
        } else {
            holder.frame.setPadding(16, 16, 32, 16)
        }
    }

    inner class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val progress: ProgressBar = view.findViewById(R.id.progress)
        val frame: FrameLayout = view.findViewById(R.id.grid_frame)
        val imgGrid: RoundedImageView = view.findViewById(R.id.img_grid)
        val txtCategory: TextView = view.findViewById(R.id.txt_grid_cell)
        val touchView: View = view.findViewById(R.id.grid_touch_view)
    }
}