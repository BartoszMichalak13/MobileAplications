package com.example.kolkoikrzyzyk

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UiState(
    val TFval: MutableState<String> = mutableStateOf("Hello There"),
    var makeGame: Boolean = false,
)

class TTTViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    fun onTFChanged(TFval: String) {
        _uiState.update { currentState ->
            currentState.copy(
                TFval = mutableStateOf(TFval)
            )
        }
    }

    fun checkValue(): String? {
        try {
            uiState.value.TFval.value.toInt()

        }catch (Exception: java.lang.Exception){
            println("Error in checkValue")
            return null
        }
        return uiState.value.TFval.value

    }
}