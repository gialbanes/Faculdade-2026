package com.fatec.at2_base.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fatec.at2_base.model.Movie

@Composable
fun MovieItem(
    movie: Movie,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val bgColor by animateColorAsState(
        targetValue = MaterialTheme.colorScheme.surface,
        animationSpec = tween(300), label = "bg"
    )

    Card (
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row (
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.Top,
        ) {
            Icon(
                Icons.Default.PlayArrow, "Filme",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 2.dp).size(20.dp)
            )

            Column (
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp)
            ) {
                Text(
                    text = movie.title, fontSize = 14.sp, fontWeight = FontWeight.Medium,
                    maxLines = 1, overflow = TextOverflow.Ellipsis
                )

                if (movie.genre.isNotBlank()) {
                    Text(
                        text = movie.genre, fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2, overflow = TextOverflow.Ellipsis
                    )
                }
            }

            IconButton (
                onClick = onDelete, modifier = Modifier.size(32.dp)
            ) {
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
fun PreviewMovieItem() {
    MaterialTheme {
        MovieItem(
            movie = Movie(1, "Interestelar", "Ficção Científica / Drama"),
            onDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMovieItem2() {
    MaterialTheme {
        MovieItem(
            movie = Movie(2, "O Fabuloso Destino de Amélie Poulain"),
            onDelete = {}
        )
    }
}