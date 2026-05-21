package com.fatec.notes.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatec.notes.ui.components.ValidationBanner
import com.fatec.notes.viewmodel.QuestionFormViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QuestionFormScreen(
    onBack: () -> Unit,
    viewModel: QuestionFormViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    MaterialTheme {
        Surface (modifier = Modifier.fillMaxSize()){
            Column (
                modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp)
            ){
                // Cabeçalho
                Row (verticalAlignment = Alignment.CenterVertically){
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                    Text(
                        "Cadastrar Questão",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // Conteudo com scroll
                Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                    // Banner de erros
                    ValidationBanner(errors = state.allErrors)

                    // Quando der sucesso
                    AnimatedVisibility(
                        visible = state.showSuccess,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                            ),
                            modifier = Modifier.padding(16.dp),
                            verticalAligment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                "Sucesso", tint = MaterialTheme.colorScheme.primary,
                            )
                            Text(
                                "Questão salva com sucesso!",
                                modifier = Modifier.weight(1f),
                            )
                            IconButton(onClick = {viewModel.dismissSuccess()}) {
                                Icon(
                                    Icons.Default.Close,
                                    "Fechar", modifier = Modifier.weight(1f)
                                )
                            }

                        }
                    }
                }


            }
        }
    }
}