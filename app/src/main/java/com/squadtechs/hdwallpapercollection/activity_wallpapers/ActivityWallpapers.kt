package com.squadtechs.hdwallpapercollection.activity_wallpapers

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.squadtechs.hdwallpapercollection.R
import com.squadtechs.hdwallpapercollection.main_activity.fragment.WallpaperAdapter
import com.squadtechs.hdwallpapercollection.main_activity.fragment.WallpaperModel

class ActivityWallpapers : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WallpaperAdapter
    private lateinit var list: ArrayList<WallpaperModel>
    private val collectionReference = FirebaseFirestore.getInstance().collection("wallpapers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallpapers)
        initView()
        prepareToolbar()
        populateRecyclerView()
    }

    private fun populateRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        val category_key = intent!!.extras!!.getString("category_key")
        collectionReference.orderBy("server_time_stamp", Query.Direction.DESCENDING)
            .whereEqualTo("category_ref", category_key).get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                list.clear()
                if (task.isSuccessful) {
                    val querySnapshot: QuerySnapshot? = task.result
                    if (querySnapshot != null && !querySnapshot.isEmpty) {
                        for (i in querySnapshot.documents) {
                            val obj: WallpaperModel = i.toObject(WallpaperModel::class.java)!!
                            obj.wallpaper_key = i.id
                            list.add(obj)
                        }
                    } else {
                        Toast.makeText(this, "No wallpaper found", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, task.exception!!.message!!, Toast.LENGTH_LONG).show()
                    Log.i("dxdiag", task.exception!!.message!!)
                }
                adapter.notifyDataSetChanged()
            }
    }

    private fun prepareToolbar() {
        toolbar.title = "${intent!!.extras!!.getString("category_name")} Wallpapers"
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initView() {
        toolbar = findViewById(R.id.toolbar)
        recyclerView = findViewById(R.id.recycler_view)
        list = ArrayList()
        adapter = WallpaperAdapter(this, this@ActivityWallpapers, list)
    }

}
