package com.fatec.notes.ui.screen

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatec.notes.ui.components.NoteItem
import com.fatec.notes.viewmodel.NoteViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NoteScreen (viewModel: NoteViewModel = koinViewModel()) {
    val state by viewModel.uiState.collectAsState()

    MaterialTheme {
        Surface (modifier = Modifier.fillMaxSize()) {
            Column (
                modifier = Modifier.fillMaxSize().statusBarsPadding().padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text("Minhas notas", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                HorizontalDivider() // semelhante ao hr
                Text("${state.totalNotes} nota(s)", fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)

                OutlinedTextField(
                    value = state.inputTitle,
                    onValueChange = {viewModel.onTitleChange(it)},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {Text("Título da nota")},
                    singleLine = true, shape = RoundedCornerShape(8.dp)
                )
                OutlinedTextField(
                    value = state.inputContent,
                    onValueChange = {viewModel.onContentChange(it)},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {Text("Conteúdo (opcional)...")},
                    minLines = 2, maxLines = 3, shape = RoundedCornerShape(8.dp)
                )
                Button(onClick = {viewModel.addNote()}, modifier = Modifier.fillMaxWidth()) {
                    Text("Adicionar nota")
                }

                if (state.isEmpty) {
                    Box(Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
                        Text("Nenhuma nota criada", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                } else {
                    LazyColumn (verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                        items(items = state.notes, key = {it.id}) { note ->
                            NoteItem(
                                note = note,
                                onDelete = { viewModel.removeNote(note.id) },
                                modifier = Modifier.animateItem(
                                    fadeInSpec = tween(300),
                                    fadeOutSpec = tween(300)
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}