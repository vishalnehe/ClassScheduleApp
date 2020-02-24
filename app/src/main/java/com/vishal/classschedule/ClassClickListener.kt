package com.vishal.classschedule

import android.view.View

interface ClassClickListener {
    fun onClassClickListener(view: View, position: Int, addDrop: Boolean)
}