package com.edrem.chamba.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.edrem.chamba.ui.models.ServiceRequest
import com.edrem.chamba.ui.theme.ActionOrange

@Composable
fun RequestCard(request: ServiceRequest, onReviewClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(request.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(request.status, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary, modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(request.date, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                if (request.assignedProName != null) {
                    Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                        Text(request.assignedProName.take(1), color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelMedium)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(request.assignedProName, style = MaterialTheme.typography.bodyMedium)
                } else {
                    Text("Esperando cotizaciones...", style = MaterialTheme.typography.bodyMedium, color = ActionOrange, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.weight(1f))
                
                if (request.status == "Completadas") {
                    TextButton(onClick = onReviewClick) { Text("Calificar") }
                } else {
                    TextButton(onClick = { }) { Text("Ver detalle") }
                }
            }
        }
    }
}
