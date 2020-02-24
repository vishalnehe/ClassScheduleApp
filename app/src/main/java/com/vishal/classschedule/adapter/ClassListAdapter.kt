package com.vishal.classschedule.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.vishal.classschedule.ClassClickListener
import com.vishal.classschedule.R
import com.vishal.classschedule.model.ClassData

class ClassListAdapter(private var activity: FragmentActivity?, private var classes: ArrayList<ClassData?>, var classClickListener: ClassClickListener, var enable: Boolean): BaseAdapter() {
    private class ViewHolder(row: View?){
        var classNo: TextView? = null
        var classTitle: TextView? = null
        var classroomNo: TextView? = null
        var classStartTime: TextView? = null
        var add: Button? = null
        var drop: Button? = null

        init {
            this.classNo = row?.findViewById(R.id.classNo)
            this.classroomNo = row?.findViewById(R.id.classroomNo)
            this.classTitle = row?.findViewById(R.id.classTitle)
            this.classStartTime = row?.findViewById(R.id.classStartTime)
            this.add = row?.findViewById(R.id.add)
            this.drop = row?.findViewById(R.id.drop)

        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder
        if(convertView == null){
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.class_list_row, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        var classData = classes[position]
        viewHolder.classNo?.text = classData?.classNo
        viewHolder.classTitle?.text = classData?.classTitle
        viewHolder.classroomNo?.text = classData?.classRoomNo
        viewHolder.classStartTime?.text = classData?.classStartTime

        if(enable){
            viewHolder.add?.visibility = View.VISIBLE
            viewHolder.drop?.visibility = View.VISIBLE
            if(classData?.classAdded!!){
                viewHolder.add?.isEnabled = false
                viewHolder.drop?.isEnabled = true
            }else{
                viewHolder.add?.isEnabled = true
                viewHolder.drop?.isEnabled = false
            }
            notifyDataSetChanged()
        }

        viewHolder.add?.setOnClickListener {
            classClickListener.onClassClickListener(it,position,true)
            notifyDataSetChanged()
        }
        viewHolder.drop?.setOnClickListener {
            classClickListener.onClassClickListener(it,position,false)
            notifyDataSetChanged()
        }

        return view as View
    }

    override fun getItem(position: Int): ClassData? {
        return classes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return classes.size
    }

}