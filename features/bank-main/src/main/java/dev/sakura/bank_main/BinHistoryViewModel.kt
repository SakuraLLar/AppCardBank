package dev.sakura.bank_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sakura.bank.data.BinRepository
import dev.sakura.bank.database.entity.BinHistoryEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BinHistoryViewModel @Inject constructor(
    private val repository: BinRepository,
) : ViewModel() {
    private val _history = MutableStateFlow<List<BinHistoryEntity>>(emptyList())
    val history = _history.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getHistory().collectLatest { history ->
                _history.value = history
            }
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            repository.clearHistory()
        }
    }
}
