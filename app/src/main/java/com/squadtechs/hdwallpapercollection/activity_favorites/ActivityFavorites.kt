package com.squadtechs.hdwallpapercollection.activity_favorites

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squadtechs.hdwallpapercollection.R
import com.squadtechs.hdwallpapercollection.main_activity.fragment.WallpaperAdapter
import com.squadtechs.hdwallpapercollection.main_activity.fragment.WallpaperModel

class ActivityFavorites : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WallpaperAdapter
    private lateinit var list: ArrayList<WallpaperModel>
    private lateinit var pref: SharedPreferences
    private val collectionReference = FirebaseFirestore.getInstance().collection("wallpapers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        initViews()
        populateToolbar()
        populateRecyclerVIew()
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
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        list = ArrayList()
        adapter = WallpaperAdapter(this, this@ActivityFavorites, list, true)
        pref = getSharedPreferences("favorite", Context.MODE_PRIVATE)
    }

}
