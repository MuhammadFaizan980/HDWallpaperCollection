package com.squadtechs.hdwallpapercollection.main_activity.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squadtechs.hdwallpapercollection.R

class FragmentMain : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val txt: TextView = view.findViewById(R.id.txt_abc)
        txt.text = arguments!!.getInt("position", -1).toString()
        return view
    }


}
