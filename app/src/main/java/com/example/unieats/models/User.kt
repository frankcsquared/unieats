package com.example.unieats.models

data class History(
    val date: Int = 0,
    val foodId: String = ""
)

data class User(
    val id: String ="",
    val first_name: String ="",
    val last_name: String ="",
    val history: Map<String, History> = mapOf<String, History>("" to History(0,"")),
    val username: String ="",
    val password: String ="",
    val goal: String = "")

data class Location(
    val id: String ="",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val name: String = "")