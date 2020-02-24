package com.vishal.classschedule.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vishal.classschedule.ClassClickListener
import com.vishal.classschedule.ClassRepository
import com.vishal.classschedule.R
import com.vishal.classschedule.adapter.ClassListAdapter
import kotlinx.android.synthetic.main.fragment_classes.*

class ClassesFragment : Fragment(), ClassClickListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_classes, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var adapter = ClassListAdapter(activity,
            ClassRepository.getClasses(),this, true)
        list_view_classes.adapter = adapter

        adapter.notifyDataSetChanged()

    }

    override fun onClassClickListener(view: View, position: Int, addDrop: Boolean) {
        println(ClassRepository.getClasses()[position]?.classNo)
        if(addDrop)
            ClassRepository.addClass(position)
        else
            ClassRepository.dropClass(position)
    }
}