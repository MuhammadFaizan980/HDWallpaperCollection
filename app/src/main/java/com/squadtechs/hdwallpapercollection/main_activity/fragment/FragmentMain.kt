package com.squadtechs.hdwallpapercollection.main_activity.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squadtechs.hdwallpapercollection.R

class FragmentMain : Fragment() {
    private lateinit var collectionReference: CollectionReference
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        initViews(view)
        populateRecyclerView(view)
        return view
    }

    private fun populateRecyclerView(view: View) {
        if (arguments!!.getInt("position", -1) == 0) {
            val list = ArrayList<CategoryModel>()
            val adapter = CategoryAdapter(activity!!.applicationContext, activity!!, list)
            recyclerView.layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
            recyclerView.adapter = adapter
            collectionReference.orderBy("server_time_stamp", Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    list.clear()
                    if (firebaseFirestoreException == null && querySnapshot != null && !querySnapshot.isEmpty) {
                        for (i in querySnapshot.documents) {
                            val obj: CategoryModel = i.toObject(CategoryModel::class.java)!!
                            obj.category_key = i.id
                            list.add(obj)
                        }
                    } else {
                        Toast.makeText(
                            activity!!.applicationContext,
                            firebaseFirestoreException!!.message!!,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    adapter.notifyDataSetChanged()
                }
        } else {
            //TODO: Populate new wallpaper later
            val list = ArrayList<WallpaperModel>()
            val adapter = WallpaperAdapter(activity!!.applicationContext, activity!!, list)
            recyclerView.layoutManager = GridLayoutManager(activity!!.applicationContext, 2)
            recyclerView.adapter = adapter
            collectionReference.orderBy("server_time_stamp", Query.Direction.DESCENDING)
                .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    list.clear()
                    if (firebaseFirestoreException == null && querySnapshot != null && !querySnapshot.isEmpty) {
                        for (i in querySnapshot.documents) {
                            val obj: WallpaperModel = i.toObject(WallpaperModel::class.java)!!
                            obj.wallpaper_key = i.id
                            if (((System.currentTimeMillis() / 1000) - obj.server_time_stamp!!.seconds) <= 604800) {
                                list.add(obj)
                            }
                        }
                    } else {
                        Toast.makeText(
                            activity!!.applicationContext,
                            firebaseFirestoreException!!.message!!,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    adapter.notifyDataSetChanged()
                }
        }
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        collectionReference = if (arguments!!.getInt("position", -1) == 0) {
            FirebaseFirestore.getInstance().collection("categories")
        } else {
            FirebaseFirestore.getInstance().collection("wallpapers")
        }
    }

}
