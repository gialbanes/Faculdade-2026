package com.example.calculadora.ui.screens.calculadora

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.calculadora.models.Calculadora
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.CardDefaults

@Composable
fun CalculadoraScreen(modifier: Modifier = Modifier){
    // salva o estado da calc se o cel rotacionar
    var calcState by rememberSaveable { mutableStateOf(Calculadora(0.0, 0.0, 0.0)) }
    var text1 by rememberSaveable { mutableStateOf("") }
    var text2 by rememberSaveable { mutableStateOf("") }

    CalculadoraContent(
        state = calcState,
        num01 = text1,
        num02 = text2,
        // quando o user digitar o número a variável é atualizada
        onNum01Change = { text1 = it},
        onNum02Change = {text2 = it},
        onOperacao = { operacao ->
            // converte texto p número
            val n1 = text1.toDoubleOrNull() ?: 0.0
            val n2 = text2.toDoubleOrNull() ?: 0.0

            val calc = Calculadora(num01 = n1, num02 = n2)

            val resultado = when(operacao){
                "somar" -> calc.somar()
                "subtrair" -> calc.subtrair()
                "multiplicar" -> calc.multiplicar()
                "dividir" -> calc.dividir()
                else -> 0.0
        }
            // faz o resultado aparecer na tela
            calcState = calc.copy(resultado = resultado)
        },
        modifier = modifier
    )
}

@Composable
// a interface
fun CalculadoraContent(
    state: Calculadora,
    num01: String,
    num02: String,
    onNum01Change: (String) -> Unit,
    onNum02Change: (String) -> Unit,
    onOperacao: (String) -> Unit,
    modifier: Modifier  = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp), // margem da tela
        verticalArrangement = Arrangement.spacedBy(16.dp) // espaço entre os itens na tela
    ) {
        Text(
            text = "Calculadora",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        // campo do text1
        OutlinedTextField(
            value = num01,
            onValueChange = onNum01Change,
            label = { Text("Número 1")},
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        // campo text2
        OutlinedTextField(
            value = num02,
            onValueChange = onNum02Change,
            label = { Text("Número 2") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )

        // botões das operações
        Column{
            // primeira linha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { onOperacao("somar") }, modifier = Modifier.weight(1f)) { Text("+") }
                Button(onClick = { onOperacao("subtrair") }, modifier = Modifier.weight(1f)) { Text("-") }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // segunda linha
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { onOperacao("multiplicar") }, modifier = Modifier.weight(1f)) { Text("*") }
                Button(onClick = { onOperacao("dividir") }, modifier = Modifier.weight(1f)) { Text("/") }
            }
        }

        // resultado
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Resultado:",
                    style = MaterialTheme.typography.displayMedium
                    )
                Text(
                    text = "${state.resultado}",
                    style = MaterialTheme.typography.displayMedium
                )
            }
        }
    }
}