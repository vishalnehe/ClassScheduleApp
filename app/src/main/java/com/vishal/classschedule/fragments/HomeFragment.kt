package com.vishal.classschedule.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.vishal.classschedule.ClassClickListener
import com.vishal.classschedule.ClassRepository
import com.vishal.classschedule.R
import com.vishal.classschedule.adapter.ClassListAdapter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), ClassClickListener {

    private var sharedPreferences: SharedPreferences? = null
    private val sharePrefs: String = "com.vishal.classschedule"
    private val sharedPrefNameKey = "sName"
    private val sharedPrefEmailKey = "sEmail"
    private val sharedPrefRedIdKey = "sRedId"
    private var nameText: TextView? = null
    private var emailText: TextView? = null
    private var redIdText: TextView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_home, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nameText = view?.findViewById(R.id.studentName)
        emailText = view?.findViewById(R.id.studentEmail)
        redIdText = view?.findViewById(R.id.studentRedId)

        sharedPreferences = this.activity?.getSharedPreferences(sharePrefs, Context.MODE_PRIVATE)
        nameText?.text = sharedPreferences?.getString(sharedPrefNameKey, "").toString()
        emailText?.text = sharedPreferences?.getString(sharedPrefEmailKey, "").toString()
        redIdText?.text = sharedPreferences?.getString(sharedPrefRedIdKey, "").toString()

        var adapter = ClassListAdapter(activity,
            ClassRepository.getSelectedClasses(),this, false)
        list_view_home.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onClassClickListener(view: View, position: Int, addDrop: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}