/*
 * TaskStats.kt - Componentes de Estatísticas da Lista de Tarefas
 * 
 * Este arquivo contém:
 * - StatCard: Composable que exibe uma estatística individual com animação de transição
 * - TaskStateRow: Composable que exibe três cards lado a lado (Total, Feitas, Pendentes)
 * Utiliza Jetpack Compose e animações para visualizar o progresso das tarefas
 */

package com.fatec.todolist.ui.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun StatCard(label: String, value: Int, modifier: Modifier = Modifier) {
    // animacao pra toda vez que alterar o estado
    val animatedValue by animateIntAsState (
        targetValue = value,
        animationSpec = tween(300), // duracao da animacao
        label = "stat"
    )

    Card(modifier = modifier) {
        Column(Modifier.padding(16.dp)) {
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(4.dp))
            Text(animatedValue.toString(), fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TaskStateRow(total: Int, done: Int, pending: Int){
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        StatCard("Total", total, Modifier.weight(1f))
        StatCard("Feitas", done, Modifier.weight(1f))
        StatCard("Pendentes", pending, Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewStats() {
    MaterialTheme { TaskStateRow(total = 5, done = 3, pending = 2) }
}