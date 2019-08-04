package com.squadtechs.hdwallpapercollection.activity_view_wallpapers

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
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
    private val collectionReference = FirebaseFirestore.getInstance().collection("wallpapers")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiew_wallpaper)
        initViews()
        getData()
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
                } else {
                    Toast.makeText(this, task.exception!!.message!!, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun initViews() {
        viewPager = findViewById(R.id.viewpager_view_wallpaper)
        list = ArrayList()
    }

}
