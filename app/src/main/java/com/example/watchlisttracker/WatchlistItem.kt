package com.example.watchlisttracker

data class WatchlistItem(
    val id: Int,
    val title: String,
    val description: String,
    val status: Status
)