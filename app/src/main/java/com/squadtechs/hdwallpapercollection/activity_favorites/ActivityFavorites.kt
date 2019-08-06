package com.squadtechs.hdwallpapercollection.activity_favorites

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squadtechs.hdwallpapercollection.R
import com.squadtechs.hdwallpapercollection.main_activity.fragment.WallpaperAdapter
import com.squadtechs.hdwallpapercollection.main_activity.fragment.WallpaperModel

class ActivityFavorites : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var txtInfo: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WallpaperAdapter
    private lateinit var list: ArrayList<WallpaperModel>
    private lateinit var pref: SharedPreferences
    private lateinit var bannerAd: AdView
    private val collectionReference = FirebaseFirestore.getInstance().collection("wallpapers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        initViews()
        populateToolbar()
        populateRecyclerVIew()
        initAd()
    }

    private fun populateRecyclerVIew() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        collectionReference.orderBy("server_time_stamp", Query.Direction.DESCENDING)
            .addSnapshotListener(this@ActivityFavorites) { querySnapshot, firebaseFirestoreException ->
                list.clear()
                if (firebaseFirestoreException == null && !querySnapshot!!.isEmpty) {
                    for (i in querySnapshot.documents) {
                        Log.i("dxdiag", i.data.toString())
                        if (pref.getBoolean(i.id, false)) {
                            val obj = i.toObject(WallpaperModel::class.java)
                            obj!!.wallpaper_key = i.id
                            list.add(obj)
                        }
                    }
                    if (list.isEmpty()) {
                        txtInfo.visibility = View.VISIBLE
                    }

                } else {
                    Toast.makeText(this, firebaseFirestoreException!!.message!!, Toast.LENGTH_LONG).show()
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun populateToolbar() {
        toolbar.title = "Favorite Wallpapers"
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initViews() {
        txtInfo = findViewById(R.id.txt_info)
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        list = ArrayList()
        adapter = WallpaperAdapter(this, this@ActivityFavorites, list, true, false)
        pref = getSharedPreferences("favorite", Context.MODE_PRIVATE)
        bannerAd = findViewById(R.id.banner_ad)
    }

    override fun onStart() {
        super.onStart()
        txtInfo.visibility = View.GONE
    }

    private fun initAd() {
        val adRequest: AdRequest = AdRequest.Builder().build()
        bannerAd.loadAd(adRequest)
    }

}
