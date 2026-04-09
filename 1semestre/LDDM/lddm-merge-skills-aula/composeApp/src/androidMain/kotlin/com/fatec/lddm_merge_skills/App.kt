package com.fatec.lddm_merge_skills

import android.R
import android.view.RoundedCorner
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import lddm_merge_skills.composeapp.generated.resources.Res

// ─── Cores shadcn/ui (tema claro) ───
private val Border = Color(0xFFE5E7EB)
private val Muted = Color(0xFF6B7280)

@Composable
fun App() {
    DashboardScreenPreview()
    MaterialTheme {}
}

@Composable
@Preview
fun DashboardScreenPreview(){
    // Estados
    // parecido com o useState do JS
    var coursesCount by remember { mutableStateOf(0) }
    var lessonsCount by remember { mutableStateOf(0) }
    var questionsCount by remember { mutableStateOf(0) }
    var loading by remember { mutableStateOf(true) }

    // rememberCoroutineScope: pra manter assíncrono sem congelar a tela, sem ele, isso acontece
    val scope = rememberCoroutineScope ()

    fun refresh(){
        scope.launch {
            loading = true

            try{
                val courses = ApiClient.getCourses()
                coursesCount = courses.size

                var totalLesson = 0
                var totalQuestions = 0

                for (course in courses){
                    val lessons = ApiClient.getLessons(course.id)
                    totalLesson += lessons.size
                    for (lesson in lessons){
                        val questions = ApiClient.getQuestions(lesson.id)
                        totalQuestions += questions.size
                    }
                }

                lessonsCount = 0
                questionsCount = 0

            } catch(e: Exception){
                e.printStackTrace()
            }
            loading = false
        }
    }

        LaunchedEffect(key1 = Unit) {
            refresh()
        }

        // pra nao ficar com cor transparente
        // fillMaxSize: preenche espaco disponivel
        Surface (modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(
                modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Header
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Painel principal", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        Text("Visão geral do sistema", fontSize = 14.sp, color = Muted)
                    }
                    OutlinedButton(onClick = {}){
                        Text("Atualizar", color = Color.Black)
                    }
                }
                HorizontalDivider(color= Border)

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    DashboardCard("Cursos", coursesCount.toString(), Modifier.weight(1f))
                    DashboardCard("Lições", lessonsCount.toString(), Modifier.weight(1f))
                    DashboardCard("Questões", questionsCount.toString(), Modifier.weight(1f))
                }
            }
        }
    }

@Composable
fun DashboardCard(title: String, value: String, modifier: Modifier = Modifier){
    OutlinedCard(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp), // como se fosse o border radius
        border = BorderStroke(1.dp, Border),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(title, fontSize = 13.sp, color = Muted, fontWeight = FontWeight.Medium) // deixa a fonte dinâmica
            Spacer(Modifier.height(4.dp))
            Text(value, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        }
    }
}