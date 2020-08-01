package com.example.unieats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController

class LogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        var intentLocation = intent.extras?.getInt("chosenId")

        if (intentLocation != null) {
            locationId = intentLocation
        }
    }

    companion object {
        var locationId : Int = -1
    }
}