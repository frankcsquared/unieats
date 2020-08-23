package com.example.unieats

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.unieats.models.History
import com.example.unieats.models.User
import com.example.unieats.ui.search.SearchFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    companion object {
        var history = History(0,"")
        var histMap = mapOf<String, History>("" to history)
        var selectedUser = User("","","", histMap, "", "", "0")

        //User Registration stuff
        var firstName : String = ""
        var lastName : String = ""
        var username : String = ""
        var password : String = ""
        var email : String = ""
        var uni : String = ""

        fun reset () {
            firstName = ""
            lastName = ""
            username = ""
            password = ""
            email = ""
            uni = ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val navView: BottomNavigationView = findViewById(R.id.navigationmenu)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_profile))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

    }

}

