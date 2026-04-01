package com.fatec.lddm_merge_skills

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatec.lddm_merge_skills.model.Course
import com.fatec.lddm_merge_skills.network.ApiClient
import kotlinx.coroutines.launch

// ─── Cores shadcn/ui (tema claro) ───
private val Border = Color(0xFFE5E7EB)
private val Muted = Color(0xFF6B7280)

@Composable
@Preview
fun App() {
    MaterialTheme {
        var courses by remember { mutableStateOf<List<Course>>(emptyList()) }
        var loading by remember { mutableStateOf(true) }
        var error by remember { mutableStateOf<String?>(null) }
        val scope = rememberCoroutineScope()

        fun refresh() {
            scope.launch {
                loading = true
                error = null
                try {
                    courses = ApiClient.getCourses()
                } catch (e: Exception) {
                    error = e.message
                }
                loading = false
            }
        }

        LaunchedEffect(Unit) { refresh() }

        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Courses", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                        Text("All available courses.", fontSize = 14.sp, color = Muted)
                    }
                    OutlinedButton(
                        onClick = { refresh() },
                        shape = RoundedCornerShape(6.dp),
                        border = BorderStroke(1.dp, Border)
                    ) { Text("Refresh", fontSize = 13.sp, color = Color.Black) }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 12.dp),
                    color = Border
                )

                // Conteúdo
                when {
                    loading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                strokeWidth = 2.dp,
                                color = Muted
                            )
                        }
                    }
                    error != null -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("Connection failed", fontWeight = FontWeight.Medium)
                                Spacer(Modifier.height(4.dp))
                                Text(error.orEmpty(), fontSize = 13.sp, color = Muted)
                                Spacer(Modifier.height(12.dp))
                                OutlinedButton(
                                    onClick = { refresh() },
                                    shape = RoundedCornerShape(6.dp),
                                    border = BorderStroke(1.dp, Border)
                                ) { Text("Try again", fontSize = 13.sp, color = Color.Black) }
                            }
                        }
                    }
                    else -> {
                        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(courses) { course ->
                                OutlinedCard(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, Border),
                                    colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
                                ) {
                                    Column(Modifier.padding(14.dp)) {
                                        Text(
                                            course.title,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 14.sp
                                        )
                                        val desc = course.description
                                        if (!desc.isNullOrBlank()) {
                                            Text(desc, fontSize = 13.sp, color = Muted)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}