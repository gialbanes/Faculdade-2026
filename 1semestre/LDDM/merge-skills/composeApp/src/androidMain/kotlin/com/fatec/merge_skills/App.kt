package com.fatec.merge_skills

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatec.merge_skills.model.Course
import com.fatec.merge_skills.network.ApiClient

@Composable
@Preview
fun App() {
    MaterialTheme {
        // Estados reativos
        var courses by remember { mutableStateOf<List<Course>>(emptyList()) }
        var loading by remember { mutableStateOf(true) }
        var error by remember { mutableStateOf<String?>(null) }

        // Busca os cursos UMA vez ao montar a tela
        LaunchedEffect(Unit) {
            try {
                courses = ApiClient.getCourses()
            } catch (e: Exception) {
                error = e.message
            }
            loading = false
        }

        // Renderiza baseado no estado
        when {
            loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Erro: $error", fontSize = 16.sp)
                }
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(courses) { course ->
                        Text("• ${course.title}", fontSize = 16.sp)
                    }
                }
            }
        }
    }
}