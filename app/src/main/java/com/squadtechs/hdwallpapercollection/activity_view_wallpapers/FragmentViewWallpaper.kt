package com.squadtechs.hdwallpapercollection.activity_view_wallpapers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.squadtechs.hdwallpapercollection.R

class FragmentViewWallpaper : Fragment() {
    private lateinit var imgViewWallPaper: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var url: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragmet_view_wallpaper, container, false)
        initView(view)
        loadImage()
        return view
    }

    private fun loadImage() {
        val requestQueue = Volley.newRequestQueue(activity!!.applicationContext)
        val imageRequest = ImageRequest(
            url,
            Response.Listener { response ->
                progressBar.visibility = View.GONE
                imgViewWallPaper.setImageBitmap(response)
            },
            0,
            0,
            ImageView.ScaleType.CENTER,
            null,
            Response.ErrorListener { error ->
                progressBar.visibility = View.GONE
                Toast.makeText(
                    activity!!.applicationContext,
                    "There was an error loading this wallpaper",
                    Toast.LENGTH_LONG
                ).show()
            }).setRetryPolicy(
            DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
        )
        requestQueue.add(imageRequest)
    }

    private fun initView(view: View) {
        imgViewWallPaper = view.findViewById(R.id.img_view_wallpaper)
        progressBar = view.findViewById(R.id.progress_view_image)
        url = arguments!!.getString("url")!!
    }

}
