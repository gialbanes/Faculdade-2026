package com.fatec.lddm_merge_skills

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

// ─── Cores shadcn/ui (tema claro) ───
private val Border = Color(0xFFE5E7EB)
private val Muted = Color(0xFF6B7280)

@Composable
@Preview
fun AddCourseScreenPreview() {
    var title by remember { mutableStateOf(" ")}
    var description by remember { mutableStateOf("") }

    MaterialTheme{
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
            Column(modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)) {
                // Header
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedButton(onClick = {}) { Text("\u276E", color = Color.Black, fontSize = 16.sp) }
                    Column {
                        Text("Novo curso", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
                        Text("Preencha os detalhes abaixo", fontSize = 14.sp, color = Muted) // "Muted" vem dos valores definidos no topo!
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Border);

                // Formulário
                Text("Título", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(value = title, onValueChange = { title = it}, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(16.dp))

                Text("Descrição", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Spacer(Modifier.height(4.dp))
                OutlinedTextField(value = description, onValueChange = { description = it}, modifier = Modifier.fillMaxWidth())

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White)
                ){
                    Text("Salvar curso")

                }

            }
        }
    }
}