package com.vishal.classschedule

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vishal.classschedule.fragments.ClassesFragment
import com.vishal.classschedule.fragments.HomeFragment
import com.vishal.classschedule.fragments.StudentFragment

class MainActivity : AppCompatActivity() {

    private var sharedPreferences: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null
    private val sharePrefs: String = "com.vishal.classschedule"
    private var selectedClasses: Set<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * Load data from JSON
         */
        val jsonString = application?.assets?.open("classes.json")?.bufferedReader().use {
            it?.readText()
        }
        ClassRepository.parseClasses(jsonString)

        /**
         * Get saved data
         */
        sharedPreferences = getSharedPreferences(sharePrefs, Context.MODE_PRIVATE)
        selectedClasses = sharedPreferences?.getStringSet("classSetKey", null)

        if(selectedClasses != null){
            (selectedClasses as MutableSet<String>).forEach {
                ClassRepository.addClass(it)
            }
        }


        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNav.setOnNavigationItemSelectedListener(navListener)
        if (savedInstanceState == null) {
            val fragment = HomeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
                .commit()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        sharedPreferences = getSharedPreferences(sharePrefs, Context.MODE_PRIVATE)
        mEditor = sharedPreferences?.edit()
        val classSet = HashSet<String?>()
        for (i in 0 until ClassRepository.getSelectedClasses().size){
            classSet.add(ClassRepository.getSelectedClasses()[i]?.classNo)
        }
        println(classSet.size)
        mEditor?.putStringSet("classSetKey", classSet)
        mEditor?.apply()
        ClassRepository.clearSelectedClasses()

    }

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when(menuItem.itemId) {
            R.id.nav_home -> {
                val fragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_student -> {
                val fragment =
                    StudentFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_classes -> {
                val fragment =
                    ClassesFragment()
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
