package com.example.watchlisttracker

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class WatchViewModel : ViewModel() {
    //храним текущее состояние
    private val _uiState = MutableStateFlow(WatchlistUiState(items = loadInitialItems()))

    val uiState: StateFlow<WatchlistUiState> = _uiState.asStateFlow()

    //списочек аниме + их описание
    private fun loadInitialItems(): List<WatchlistItem> {
        return listOf(
            WatchlistItem(
                id = 1,
                title = "Стальной алхимик",
                description = "2009, студия Bones, 64 эп.",
                status = Status.DONE
            ),
            WatchlistItem(
                id = 2,
                title = "Атака титанов",
                description = "2013, студия WIT, 87 эп.",
                status = Status.WATCHING
            ),
            WatchlistItem(
                id = 3,
                title = "Клинок, рассекающий демонов",
                description = "2019, студия ufotable, 55 эп.",
                status = Status.PLANNED
            ),
            WatchlistItem(
                id = 4,
                title = "Тетрадь смерти",
                description = "2006, студия Madhouse, 37 эп.",
                status = Status.DONE
            ),
            WatchlistItem(
                id = 5,
                title = "Ванпанчмен",
                description = "2015, студия Madhouse, 12 эп.",
                status = Status.PLANNED
            ),
            WatchlistItem(
                id = 6,
                title = "Ходячий замок",
                description = "2004, реж. Хаяо Миядзаки",
                status = Status.WATCHING)
        )
    }

    fun updateQuery(newQuery: String) {
        _uiState.update { currentState ->
            currentState.copy(query = newQuery)
        }
    }

    //фильтруем по статусу
    fun setFilter(status: Status?) {
        _uiState.update { currentState ->
            currentState.copy(filter = status)
        }
    }

    //ищем в поиске нужное аниме и меняем статус
    fun toggleStatus(itemId: Int) {
        _uiState.update { currentState ->
            val updatedItems = currentState.items.map { item ->
                if (item.id == itemId) {
                    val nextStatus = when (item.status) {
                        Status.PLANNED -> Status.WATCHING
                        Status.WATCHING -> Status.DONE
                        Status.DONE -> Status.PLANNED
                    }
                    item.copy(status = nextStatus)
                } else {
                    item
                }
            }
            currentState.copy(items = updatedItems)
        }
    }

}