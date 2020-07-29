package com.example.unieats.ui.registration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class RegViewmodel(private val state: SavedStateHandle) : ViewModel() {
    private var name : String = ""
    private var email : String = ""
    private var uni : String = ""

    fun setName (newName : String) {
        name = newName
    }

    fun getName () : String {
        return name
    }

    fun setEmail (newEmail : String) {
        email = newEmail
    }

    fun getEmail () : String {
        return email
    }

    fun setUni (newUni : String) {
        uni = newUni
    }

    fun getUni () : String {
        return uni
    }
}