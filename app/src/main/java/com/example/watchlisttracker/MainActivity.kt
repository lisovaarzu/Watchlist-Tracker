package com.example.watchlisttracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.watchlisttracker.ui.theme.WatchlistTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchlistTrackerTheme {
                val viewModel: WatchViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()

                WatchlistScreen(
                    uiState = uiState,
                    onQueryChange = { viewModel.updateQuery(it) },
                    onFilterChange = { viewModel.setFilter(it) },
                    onStatusToggle = { viewModel.toggleStatus(it) }
                )
            }
        }
    }
}