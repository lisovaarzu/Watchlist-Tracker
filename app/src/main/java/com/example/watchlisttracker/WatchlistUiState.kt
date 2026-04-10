package com.example.watchlisttracker

data class WatchlistUiState(
    val items: List<WatchlistItem>,
    val query: String = "",
    val filter: Status? = null
)