package com.fatec.todolist.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatec.todolist.model.Task

@Composable
fun TaskItem(
    task: Task,
    onToogle: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    ) {
        val bgColor by animateColorAsState(
            targetValue = if (task.isDone) Color(0xFF8F5E9) else MaterialTheme.colorScheme.surface,
            animationSpec = tween(300), label = "bg"
    )
    Card(modifier = modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = bgColor)) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onToogle, modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.Default.Check, "Marcar",
                    tint = if (task.isDone) Color(0xFF4CAF50) else Color.LightGray
                )
            }

            Text(
                text= task.title,
                fontSize = 14.sp,
                fontWeight = if (task.isDone) FontWeight.Normal else FontWeight.Medium,
                textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None,
                color = if (task.isDone) Color.Gray else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            )
            IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                Icon(
                    Icons.Default.Delete, "Remover",
                    tint = Color(0xFFE53935), modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPending() {
    MaterialTheme { TaskItem(Task(1, "Estudar ViewModel", false), {}, {}) }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDone() {
    MaterialTheme { TaskItem(Task(2, "Criar data class", true), {}, {}) }
}