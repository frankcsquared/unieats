package com.example.unieats.models

data class History(
    val date: Int = 0,
    val foodId: String = ""
)

data class User(
    val id: String ="",
    var first_name: String ="",
    var last_name: String ="",
    val history: Map<String, History> = mapOf<String, History>("" to History(0,"")),
    val username: String ="",
    val password: String ="",
    var goal: String = "",
    val email: String = "",
    val uni: String = ""
)

data class Location(
    val id: String ="",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val name: String = "")