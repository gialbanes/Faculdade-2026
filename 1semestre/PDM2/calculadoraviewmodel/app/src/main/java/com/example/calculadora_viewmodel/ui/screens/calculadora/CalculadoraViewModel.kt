package com.example.calculadora_viewmodel.ui.screens.calculadora

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CalculadoraViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CalculadoraUiState())
    val uiState: StateFlow<CalculadoraUiState> = _uiState.asStateFlow()

    fun onAction(action: CalculadoraAction) {
        when (action) {
            is CalculadoraAction.OnNumber1Changed -> {
                _uiState.update { it.copy(numero1 = action.numero1)}
            }
            is CalculadoraAction.OnNumber2Changed -> {
                _uiState.update { it.copy(numero2 = action.numero2) }
            }
            is CalculadoraAction.OnPlusClicked -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val currentNumero1 = _uiState.value.numero1
        val currentNumero2 = _uiState.value.numero2
        val soma = currentNumero1 + currentNumero2

        _uiState.update {
            it.copy(resultado = soma)
        }
    }
}