package com.example.calculadora.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Calculadora(
    val num01: Double = 0.0,
    val num02: Double = 0.0,
    val resultado: Double = 0.0
) : Parcelable {
    fun somar(): Double = num01 + num02
    fun subtrair(): Double = num01 - num02
    fun multiplicar(): Double = num01 * num02
    fun dividir(): Double{
        return if (num02 != 0.0) num01 / num02 else 0.0
    }
}

