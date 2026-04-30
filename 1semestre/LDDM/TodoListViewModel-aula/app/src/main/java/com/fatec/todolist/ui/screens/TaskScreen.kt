package com.fatec.todolist.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fatec.todolist.ui.components.TaskStateRow
import com.fatec.todolist.viewmodel.TaskViewModel

enum class TaskFilter(val label: String) {
    ALL("Todas"), PENDING("Pendentes"), DONE("Feitas")
}

@Composable
fun TaskScreen (viewModel: TaskViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header
                Text("Minhas tarefas", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                HorizontalDivider()

                // Estatísticas
                TaskStateRow(state.totalTasks, state.doneTasks, state.pendingTasks)

                // Filtros
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewTaskScreen() {
    TaskScreen()
}