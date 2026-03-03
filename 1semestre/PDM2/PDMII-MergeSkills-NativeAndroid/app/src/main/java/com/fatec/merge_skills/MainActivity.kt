package com.fatec.merge_skills

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fatec.merge_skills.ui.theme.MergeskillskotlinTheme
import com.fatec.merge_skills.basics.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // ==========================================
        // EXECUTANDO A AULA 01 (FUNDAMENTOS KOTLIN)
        // ==========================================
        Log.i("MergeSkills", "=== TESTANDO IF e WHEN ===")
        controleDeFluxo(3.9)
        controleDeFluxo(8.5)
        controleDeFluxo(10.0)

        Log.i("MergeSkills", "=== TESTANDO ORIENTAÇÃO A OBJETOS ===")
        val aluno = AlunoNormal(nome = "Yuri", idade = 20)
        aluno.apresentar()

        val alunoVip = AlunoPremium(id = 1, nickname = "yuri_dev", beneficiosVip = true)
        alunoVip.autenticar()

        Log.i("MergeSkills", "=== TESTANDO LOOPS ===")
        // testandoLoops()
        // ==========================================
        
        enableEdgeToEdge()
        setContent {
            MergeskillskotlinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Bem-vindo ao Merge Skills Mobile! \n(Aula 01 - Hello World)",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}