/*
 * TaskViewModel - Gerenciador de Estado da Lista de Tarefas
 * 
 * Este arquivo contém:
 * - TaskUiState: data class que representa o estado da UI (lista de tarefas, texto de entrada e filtro ativo)
 * - TaskViewModel: ViewModel que gerencia as operações de tarefas (adicionar, remover, marcar como concluída, filtrar)
 *   Utiliza StateFlow para reatividade e atualização automática da UI
 */

package com.fatec.todolist.viewmodel

import com.fatec.todolist.model.Task
import com.fatec.todolist.ui.screens.TaskFilter
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val inputText: String = "",
    val currentFilter: TaskFilter = TaskFilter.ALL
) {
    val totalTasks: Int get() = tasks.size
    val doneTasks: Int get() = tasks.count { it.isDone }
    val pendingTasks: Int get() = totalTasks - doneTasks

    val filteredTasks: List<Task> get() = when (currentFilter) {
        TaskFilter.ALL -> tasks
        TaskFilter.PENDING -> tasks.filter { !it.isDone }
        TaskFilter.DONE -> tasks.filter { it.isDone }
    }
}

// classe que gerencia e armazena o estado
class TaskViewModel: ViewModel() {
    // _ so o dono da classe pode alterar ela
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    // conforme vai digitando o estado muda
    fun onInputChange(newText: String) {
        _uiState.value = _uiState.value.copy(inputText = newText)
    }

    // add a task
    fun addTask() {
        val current = _uiState.value
        if (current.inputText.isBlank()) return

        _uiState.value = current.copy(
            tasks = current.tasks + Task(title = current.inputText.trim()),
            inputText = ""
        )
    }

    // marcar a task como feita ou nao
    fun toogleTask(taskId: Long) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.map { task ->
                if(task.id == taskId) task.copy(isDone = !task.isDone) else task
            }
        )
    }

    // copia tudo e remove a task passada
    fun removetask(taskId: Long) {
        _uiState.value = _uiState.value.copy(
            tasks = _uiState.value.tasks.filter { it.id != taskId}
        )
    }

    // filtra por estado
    fun filterChange(filter: TaskFilter) {
        _uiState.value = _uiState.value.copy(currentFilter = filter)
    }
}