package com.edrem.chamba.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.edrem.chamba.ui.models.Category

@Composable
fun CategoryChip(category: Category) {
    ElevatedFilterChip(
        selected = false,
        onClick = { },
        label = { Text("${category.icon} ${category.name}", modifier = Modifier.padding(4.dp)) },
        shape = RoundedCornerShape(16.dp),
        colors = FilterChipDefaults.elevatedFilterChipColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = FilterChipDefaults.filterChipElevation(elevation = 2.dp)
    )
}
