package com.example.watchlisttracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState


@Composable
fun WatchlistItemRow(
    item: WatchlistItem,
    onStatusToggle: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Column {
                Text(
                    text = item.status.name,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onStatusToggle(item.id) }
                ) {
                    Text("Next")
                }
            }
        }
    }
}
@Composable
fun StatisticsRow(
    items: List<WatchlistItem>
) {
    val total = items.size
    val planned = items.count { it.status == Status.PLANNED }
    val done = items.count { it.status == Status.DONE }
    val watching = items.count { it.status == Status.WATCHING }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "All: $total  |  Planned: $planned  |  Done: $done  |  Watching: $watching",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
@Composable
fun FilterRow(
    currentFilter: Status?,
    onFilterChange: (Status?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        FilterChip(
            selected = currentFilter == null,
            onClick = { onFilterChange(null) },
            label = { Text("ALL") },
            modifier = Modifier.padding(end = 8.dp)
        )
        Status.entries.forEach { status ->
            FilterChip(
                selected = currentFilter == status,
                onClick = { onFilterChange(status) },
                label = { Text(status.name) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}
@Composable
fun WatchlistScreen(
    uiState: WatchlistUiState,
    onQueryChange: (String) -> Unit,
    onFilterChange: (Status?) -> Unit,
    onStatusToggle: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Anime Tracker",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = uiState.query,
            onValueChange = onQueryChange,
            label = { Text("Поколдуем?") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        StatisticsRow(items = uiState.items)

        FilterRow(
            currentFilter = uiState.filter,
            onFilterChange = onFilterChange
        )

                val filteredItems = uiState.items
            .filter { item ->
                uiState.filter == null || item.status == uiState.filter
            }
            .filter { item ->
                uiState.query.isBlank() || item.title.contains(uiState.query, ignoreCase = true)
            }

        // Список или сообщение
        if (filteredItems.isEmpty()) {
            Text(
                text = "Ничегошеньки...",
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(filteredItems) { item ->
                    WatchlistItemRow(
                        item = item,
                        onStatusToggle = onStatusToggle
                    )
                }
            }
        }
    }
}