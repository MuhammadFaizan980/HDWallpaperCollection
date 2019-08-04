package com.squadtechs.hdwallpapercollection.activity_view_wallpapers

import android.app.AlertDialog
import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.squadtechs.hdwallpapercollection.R
import com.squadtechs.hdwallpapercollection.main_activity.fragment.WallpaperModel

class ActivityViewWallpaper : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var viewPagerAdapter: MyPagerAdapter
    private lateinit var list: ArrayList<WallpaperModel>
    private lateinit var imgFavorite: ImageView
    private lateinit var imgSetWallpaper: ImageView
    private lateinit var imgDownloadWallpaper: ImageView
    private lateinit var pref: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var position = 0
    private val collectionReference = FirebaseFirestore.getInstance().collection("wallpapers")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiew_wallpaper)
        initViews()
        getData()
        setViewgaerListener()
    }

    private fun setViewgaerListener() {
        viewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(pos: Int, positionOffset: Float, positionOffsetPixels: Int) {
                position = pos
                checkFavorite()
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }

    private fun checkFavorite() {
        if (pref.getBoolean(list[position].wallpaper_key, false)) {
            imgFavorite.setImageResource(R.drawable.ic_favorite_white)
        } else {
            imgFavorite.setImageResource(R.drawable.ic_favorite_holo)
        }
    }

    private fun getData() {
        collectionReference.orderBy("server_time_stamp", Query.Direction.DESCENDING)
            .whereEqualTo("category_ref", intent!!.extras!!.getString("category_ref")).get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful) {
                    val querySnapshot: QuerySnapshot = task.result!!
                    for (i in querySnapshot.documents) {
                        val obj: WallpaperModel = i.toObject(WallpaperModel::class.java)!!
                        obj.wallpaper_key = i.id
                        list.add(obj)
                    }
                    viewPagerAdapter = MyPagerAdapter(list, supportFragmentManager)
                    viewPager.adapter = viewPagerAdapter
                    viewPager.currentItem = intent!!.extras!!.getInt("position")
                    position = intent!!.extras!!.getInt("position")
                    checkFavorite()
                    setListener()
                } else {
                    Toast.makeText(this, task.exception!!.message!!, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewpager_view_wallpaper)
        list = ArrayList()
        imgFavorite = findViewById(R.id.img_favorite)
        imgSetWallpaper = findViewById(R.id.img_set_wallpaper)
        imgDownloadWallpaper = findViewById(R.id.img_download_wallpaper)
        pref = getSharedPreferences("favorite", Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    private fun setListener() {
        imgFavorite.setOnClickListener {
            if (pref.getBoolean(list[position].wallpaper_key, false)) {
                imgFavorite.setImageResource(R.drawable.ic_favorite_holo)
                editor.putBoolean(list[position].wallpaper_key, false)
                editor.apply()
            } else {
                imgFavorite.setImageResource(R.drawable.ic_favorite_white)
                editor.putBoolean(list[position].wallpaper_key, true)
                editor.apply()
            }
        }
        imgSetWallpaper.setOnClickListener {
            val progressDialog = getProgressDialog()
            progressDialog.show()
            val requestQueue = Volley.newRequestQueue(this)
            val imageRequest = ImageRequest(
                list[position].wallpaper_image_url,
                Response.Listener { response ->
                    progressDialog.dismiss()
                    showOptionDialog(response)
//                    val wallpaperManager = WallpaperManager.getInstance(this)
//                    wallpaperManager.setBitmap(response, null, true, WallpaperManager.FLAG_LOCK)
                },
                0,
                0,
                ImageView.ScaleType.CENTER,
                null,
                Response.ErrorListener { error ->
                    progressDialog.dismiss()
                    Toast.makeText(this, "There was an error downloading this wallpaper", Toast.LENGTH_LONG).show()
                })
            requestQueue.add(imageRequest)
        }
        imgDownloadWallpaper.setOnClickListener { }
    }

    private fun showOptionDialog(bitmap: Bitmap) {
        val view = layoutInflater.inflate(R.layout.custom_dialog, null, false)
        val mDialog = AlertDialog.Builder(this)
        mDialog.setView(view)
        val cDialog = mDialog.create()
        cDialog.show()

        val mView: View = view.findViewById(R.id.touch_phone)
        val lView: View = view.findViewById(R.id.touch_lock)

        val wallpaperManager = WallpaperManager.getInstance(this)

        mView.setOnClickListener {
            cDialog.dismiss()
            wallpaperManager.setBitmap(bitmap)
            Toast.makeText(this, "Image set as device wallpaper", Toast.LENGTH_LONG).show()
        }

        lView.setOnClickListener {
            cDialog.dismiss()
            wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
            Toast.makeText(this, "Image set as lock screen wallpaper", Toast.LENGTH_LONG).show()
        }

    }

    private fun getProgressDialog(): ProgressDialog {
        val progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)
        progressDialog.setTitle("Wait")
        progressDialog.setMessage("Downloading wallpaper")
        return progressDialog
    }
}
