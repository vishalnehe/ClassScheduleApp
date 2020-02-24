package com.vishal.classschedule.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.vishal.classschedule.R
import com.vishal.classschedule.model.UserData
import kotlinx.android.synthetic.main.fragment_student.*

class StudentFragment : Fragment(), View.OnClickListener {

    private var sharedPreferences: SharedPreferences? = null
    private var mEditor: SharedPreferences.Editor? = null
    private var editTextName: TextInputLayout? = null
    private var editTextEmail: TextInputLayout? = null
    private var editTextRedId: TextInputLayout? = null
    private var confirmInputButton: Button? = null
    private var nameValue: String? = ""
    private var emailValue: String? = ""
    private var redIdValue: String? = ""
    private val sharePrefs: String = "com.vishal.classschedule"
    private val sharedPrefNameKey = "sName"
    private val sharedPrefEmailKey = "sEmail"
    private val sharedPrefRedIdKey = "sRedId"
    private lateinit var userData: UserData


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_student, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        editTextName = view?.findViewById(R.id.textInputName)
        editTextEmail = view?.findViewById(R.id.textInputEmail)
        editTextRedId = view?.findViewById(R.id.textInputRedId)
        confirmInputButton = view?.findViewById(R.id.confirmButton)
        confirmInputButton?.setOnClickListener(this)

        userData = loadPreferences()
        loadValues(userData)
    }

    /**
     * Load user data from shared preferences(i.e in app data)
     */
    private fun loadPreferences(): UserData {
        sharedPreferences = this.activity?.getSharedPreferences(sharePrefs, Context.MODE_PRIVATE)
        userData = UserData()
        userData.name = sharedPreferences?.getString(sharedPrefNameKey, "").toString()
        userData.email = sharedPreferences?.getString(sharedPrefEmailKey, "").toString()
        userData.redId = sharedPreferences?.getString(sharedPrefRedIdKey, "").toString()

        return userData
    }

    /**
     * Show user data from shared preferences(i.e in app data)
     */
    private fun loadValues(userData: UserData) {
        editTextName?.editText?.setText(userData.name)
        editTextEmail?.editText?.setText(userData.email)
        editTextRedId?.editText?.setText(userData.redId)
    }

    /**
     * Save user data in shared preferences(i.e in app data)
     */
    private fun saveData(){
        sharedPreferences = this.activity?.getSharedPreferences(sharePrefs, Context.MODE_PRIVATE)
        mEditor = sharedPreferences?.edit()
        mEditor?.putString(sharedPrefNameKey, nameValue)
        mEditor?.putString(sharedPrefEmailKey, emailValue)
        mEditor?.putString(sharedPrefRedIdKey, redIdValue)
        mEditor?.apply()

    }

    /**
     * Name Validation
     * */
    private fun validateName(): Boolean {
        nameValue = editTextName?.editText?.text.toString()
        if(editTextName?.editText?.text.toString().isEmpty()){
            textInputName.error = getString(R.string.nameEmptyErrorString)
            return false
        }else if(editTextName?.editText?.text.toString().length > 15){
            textInputName.error = getString(R.string.nameLengthErrorString)
            return false
        }else{
            textInputName.error = null
            return true
        }
    }

    /**
     * Email Validation
     * */
    private fun validateEmail(): Boolean {
        emailValue = editTextEmail?.editText?.text.toString()
        if(editTextEmail?.editText?.text.toString().isEmpty()){
            textInputEmail.error = getString(R.string.emailEmptyErrorString)
            return false
        }else{
            textInputEmail.error = null
            return true
        }
    }

    /**
     * Red Id Validation
     * */
    private fun validateRedid() : Boolean {
        redIdValue = editTextRedId?.editText?.text.toString()
        if(editTextRedId?.editText?.text.toString().isEmpty()){
            textInputRedId.error = getString(R.string.redIdEmptyErrorString)
            return false
        }else if(editTextRedId?.editText?.text.toString().length != 9){
            textInputRedId.error = getString(R.string.redIdLengthErrorString)
            return false
        }else{
            textInputRedId.error = null
            return true
        }
    }

     private fun confirmInput() {
        println("inside confirm")
        val validateName: Boolean = validateName()
        val validateEmail: Boolean = validateEmail()
        val validateRedid: Boolean = validateRedid()

        /**
         * Show error messages if any or else save data
         */
        if(!validateName || !validateEmail || !validateRedid)
            return
        saveData()

        var userDetails: String = "Name: " + textInputName.editText?.text.toString()
        userDetails += "\n"
        userDetails += "Email: " + textInputEmail.editText?.text.toString()
        userDetails += "\n"
        userDetails += "Red Id: " + textInputRedId.editText?.text.toString()
        userDetails += "\n"
        userDetails += "Data Saved!"
        Toast.makeText(this.activity,userDetails, Toast.LENGTH_LONG).show()
    }

    override fun onClick(v: View?) {
        if(v?.id == R.id.confirmButton){
            println("ins")
            confirmInput()
        }
    }
}