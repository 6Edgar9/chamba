package com.edrem.chamba.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewModal(
    proName: String,
    onDismiss: () -> Unit,
    onSubmit: (Int, List<String>) -> Unit
) {
    var rating by remember { mutableStateOf(0) }
    val tags = listOf("Puntual", "Amable", "Dejó limpio", "Experto", "Rápido", "Económico")
    val selectedTags = remember { mutableStateListOf<String>() }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Califica a $proName",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("¿Qué tal fue el servicio?", color = MaterialTheme.colorScheme.onSurfaceVariant)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Row(horizontalArrangement = Arrangement.Center) {
                for (i in 1..5) {
                    val isSelected = i <= rating
                    val scale by animateFloatAsState(targetValue = if (isSelected) 1.2f else 1.0f)
                    
                    Icon(
                        imageVector = if (isSelected) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = "Star $i",
                        tint = if (isSelected) Color(0xFFFFD700) else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(48.dp)
                            .scale(scale)
                            .clickable { rating = i }
                            .padding(4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            if (rating > 0) {
                Text("Destaca lo mejor:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.height(120.dp)
                ) {
                    items(tags) { tag ->
                        val isTagSelected = selectedTags.contains(tag)
                        FilterChip(
                            selected = isTagSelected,
                            onClick = {
                                if (isTagSelected) selectedTags.remove(tag) else selectedTags.add(tag)
                            },
                            label = { Text(tag) },
                            shape = RoundedCornerShape(16.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Button(
                    onClick = { onSubmit(rating, selectedTags) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Enviar Calificación")
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
