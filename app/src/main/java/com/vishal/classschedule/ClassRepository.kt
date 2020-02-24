package com.vishal.classschedule

import com.vishal.classschedule.model.ClassData
import org.json.JSONObject

object ClassRepository {
    private val classesList = ArrayList<ClassData?>()
    private val selectedClasses = ArrayList<ClassData?>()
    private val classMap = HashMap<String, ClassData?>()

    fun parseClasses(classesJsonObject: String?) {
        val jsonObject = JSONObject(classesJsonObject.toString())
        val classArray = jsonObject.getJSONArray("classes")
        if(classesList.isEmpty()){
            for ( i in 0 until classArray.length()){
                val classObject = classArray.getJSONObject(i)
                val newClass = ClassData(classObject.getString("class_no"), classObject.getString("class_title"), classObject.getString("classroom_no"), classObject.getString("class_start_time"),false)
                classesList.add(newClass)
                classMap.put(newClass.classNo, newClass)
                println(newClass.classTitle)
            }
        }
    }

    fun addClass(position: Int){
        classesList[position]?.classAdded = true
        selectedClasses.add(classesList[position])
    }

    fun dropClass(position: Int){
        classesList[position]?.classAdded = false
        for(i in 0 until selectedClasses.size){
            if(selectedClasses[i]?.classNo.equals(classesList[position]?.classNo)){
                selectedClasses.removeAt(i)
                break
            }
        }
    }

    fun addClass(classNo: String){
        classMap.get(classNo)?.classAdded = true
        selectedClasses.add(classMap.get(classNo))
    }

    fun clearSelectedClasses(){
        selectedClasses.clear()
    }

    fun getClasses(): ArrayList<ClassData?>{
        return classesList
    }

    fun getSelectedClasses(): ArrayList<ClassData?>{
        return selectedClasses
    }

}