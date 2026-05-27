package com.example.calculadora_viewmodel.ui.screens.calculadora

sealed interface CalculadoraAction {
    data class OnNumber1Changed(val numero1: Double) : CalculadoraAction
    data class OnNumber2Changed(val numero2: Double) : CalculadoraAction
    object OnPlusClicked : CalculadoraAction
}