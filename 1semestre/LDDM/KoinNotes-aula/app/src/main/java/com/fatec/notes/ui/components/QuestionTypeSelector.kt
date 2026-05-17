package com.fatec.notes.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fatec.notes.model.QuestionType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionTypeSelector (
    selected: QuestionType,
    onSelect: (QuestionType) -> Unit,
    modifier: Modifier = Modifier
){
    Row (
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        QuestionType.entries.forEach { type ->
            val isSelected = type == selected

            val containerColor by animateColorAsState(
                targetValue = if (isSelected)
                    MaterialTheme.colorScheme.primaryContainer
                else
                    MaterialTheme.colorScheme.surface,
                animationSpec = tween(250),
                label = "chipColor"
            )

            FilterChip(
                selected = isSelected,
                onClick = { onSelect(type) },
                label = { Text(type.label)},
                shape = RoundedCornerShape(8.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = containerColor
                ),
                modifier = Modifier.weight(1f)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMultipleChoice() {
    MaterialTheme {
        QuestionTypeSelector(
            selected = QuestionType.MULTIPLE_CHOICE,
            onSelect = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMultipleOrdering() {
    MaterialTheme {
        QuestionTypeSelector(
            selected = QuestionType.ORDERING,
            onSelect = {}
        )
    }
}