package com.fatec.notes.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OptionField (
    index: Int,
    value: String,
    error: String?,
    onChange: (String) -> Unit,
    onRemove: () -> Unit,
    canRemove: Boolean,
    modifier: Modifier = Modifier
) {
    Column (modifier = Modifier) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            OutlinedTextField(
                value = value,
                onValueChange = onChange,
                modifier = Modifier.weight(1f),
                placeholder = { Text("Alternativa ${index+1}")},
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                isError = error != null,
                supportingText = null // Exibir o erro abaixo
            )

            if (canRemove) {
                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remover alternativa ${index + 1}",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = error != null,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = error ?: "",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 16.dp, top= 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOptionFieldValid() {
    MaterialTheme {
        OptionField(
            index = 0,
            value = "Kotlin",
            error = null,
            onChange = {},
            onRemove = {},
            canRemove = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOptionFieldError() {
    MaterialTheme {
        OptionField(
            index = 1,
            value = "",
            error = "Alternativa não pode estar vazia",
            onChange = {},
            onRemove = {},
            canRemove = true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOptionFieldNoRemove() {
    MaterialTheme {
        OptionField(
            index = 0,
            value = "Java",
            error = null,
            onChange = {},
            onRemove = {},
            canRemove = false
        )
    }
}