package com.example.unieats.models;

import android.graphics.Bitmap

data class Food (
    var name: String = "",
    var image: String? = null,
    var calories: Int = 0,
    var gramsCarbs: Int = 0,
    var gramsFats: Int = 0,
    var gramsProteins: Int = 0,
    var id: String = "",
    var locationid: String = "")

