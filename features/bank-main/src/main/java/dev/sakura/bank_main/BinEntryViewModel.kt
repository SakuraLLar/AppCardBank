package dev.sakura.bank_main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sakura.bank.api.models.BinInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class BinEntryViewModel @Inject constructor(
    private val fetchBinInfoUseCase: FetchBinInfoUseCase,
) : ViewModel() {

    private val _bin = MutableStateFlow("")
    val bin: StateFlow<String> get() = _bin

    private val _binInfo = MutableStateFlow<BinInfo?>(null)
    val binInfo: StateFlow<BinInfo?> get() = _binInfo

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchBinInfo(bin: String) {
        viewModelScope.launch {
            val cleanedBin = bin.replace(" ", "")
            val result = fetchBinInfoUseCase(cleanedBin)
            result.onSuccess { binInfo ->
                _binInfo.value = binInfo
//                _error.value = null
            }.onFailure { error ->
                _error.value = error.message ?: "Произошла ошибка"
            }
        }
    }

    fun updateBin(input: String) {
        _bin.update { input }
    }
}
