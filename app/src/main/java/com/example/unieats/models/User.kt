package com.example.unieats.models

data class History(
    val date: Int = 0,
    val cals: Int = 0)

data class User(
    val id: String ="",
    val first_name: String ="",
    val last_name: String ="",
    val history: Map<String, History> = mapOf<String, History>("" to History(0,0)),
    val username: String ="",
    val password: String ="")
