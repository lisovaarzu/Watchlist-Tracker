package com.example.watchlisttracker
//название+доп.инфа+состояние
data class WatchlistItem(
    val id: Int,
    val title: String,
    val description: String,
    val status: Status
)