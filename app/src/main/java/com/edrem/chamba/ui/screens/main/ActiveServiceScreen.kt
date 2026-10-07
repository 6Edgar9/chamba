package com.edrem.chamba.ui.screens.main

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.edrem.chamba.ui.theme.ActionOrange
import com.edrem.chamba.ui.theme.TrustBlue
import com.edrem.chamba.ui.theme.TrustBlueLight

enum class ServiceStatus(val title: String) {
    ACEPTADO("Aceptado"),
    EN_CAMINO("En Camino"),
    EN_PROCESO("En Proceso"),
    FINALIZADO("Finalizado")
}

@Composable
fun ActiveServiceScreen(isChambaMode: Boolean, onBack: () -> Unit = {}) {
    var currentStatus by remember { mutableStateOf(ServiceStatus.ACEPTADO) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Timeline / Stepper
        ServiceStepper(currentStatus = currentStatus)

        // Mockup del Mapa Dinámico
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(TrustBlueLight),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Mapa en Tiempo Real\n(Ubicación del ${if (isChambaMode) "Cliente" else "Profesional"})",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        // Panel de Acciones o Información (Con animación fluida)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 16.dp
        ) {
            Crossfade(
                targetState = currentStatus,
                label = "StatusAnimation"
            ) { status ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isChambaMode) {
                        ProActionPanel(
                            status = status,
                            onStatusChange = { currentStatus = it }
                        )
                    } else {
                        ClientInfoPanel(status = status)
                    }
                }
            }
        }
    }
}

@Composable
fun ServiceStepper(currentStatus: ServiceStatus) {
    val statuses = ServiceStatus.values()
    val currentIndex = currentStatus.ordinal

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        statuses.forEachIndexed { index, status ->
            val isCompleted = index <= currentIndex
            val isCurrent = index == currentIndex
            
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(if (isCompleted) TrustBlue else MaterialTheme.colorScheme.surfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(16.dp))
                    } else {
                        Text("${index + 1}", color = MaterialTheme.colorScheme.onSurfaceVariant, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    status.title, 
                    style = MaterialTheme.typography.labelSmall, 
                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                    color = if (isCompleted) TrustBlue else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (index < statuses.size - 1) {
                HorizontalDivider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .offset(y = (-10).dp), // Alineado visualmente con los círculos
                    color = if (index < currentIndex) TrustBlue else MaterialTheme.colorScheme.surfaceVariant,
                    thickness = 2.dp
                )
            }
        }
    }
}

@Composable
fun ProActionPanel(status: ServiceStatus, onStatusChange: (ServiceStatus) -> Unit) {
    when (status) {
        ServiceStatus.ACEPTADO -> {
            Text("Has aceptado el trabajo.", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onStatusChange(ServiceStatus.EN_CAMINO) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Voy en camino", style = MaterialTheme.typography.titleMedium)
            }
        }
        ServiceStatus.EN_CAMINO -> {
            Text("Estás en ruta al destino.", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onStatusChange(ServiceStatus.EN_PROCESO) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ActionOrange)
            ) {
                Text("Llegué / Iniciar Trabajo", style = MaterialTheme.typography.titleMedium)
            }
        }
        ServiceStatus.EN_PROCESO -> {
            Text("Trabajo en ejecución.", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onStatusChange(ServiceStatus.FINALIZADO) },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981))
            ) {
                Text("Finalizar Servicio", style = MaterialTheme.typography.titleMedium)
            }
        }
        ServiceStatus.FINALIZADO -> {
            Text("¡Trabajo finalizado con éxito!", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFF10B981))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Los fondos se sumarán a tu billetera.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun ClientInfoPanel(status: ServiceStatus) {
    when (status) {
        ServiceStatus.ACEPTADO -> {
            Card(colors = CardDefaults.cardColors(containerColor = TrustBlueLight), modifier = Modifier.fillMaxWidth()) {
                Text("El profesional ha aceptado tu solicitud y se está preparando.", modifier = Modifier.padding(16.dp), color = TrustBlue, fontWeight = FontWeight.Bold)
            }
        }
        ServiceStatus.EN_CAMINO -> {
            Card(colors = CardDefaults.cardColors(containerColor = ActionOrange.copy(alpha = 0.1f)), modifier = Modifier.fillMaxWidth()) {
                Text("El profesional está en camino. Tiempo est: 15 min.", modifier = Modifier.padding(16.dp), color = ActionOrange, fontWeight = FontWeight.Bold)
            }
        }
        ServiceStatus.EN_PROCESO -> {
            Card(colors = CardDefaults.cardColors(containerColor = TrustBlueLight), modifier = Modifier.fillMaxWidth()) {
                Text("El profesional está realizando el trabajo.", modifier = Modifier.padding(16.dp), color = TrustBlue, fontWeight = FontWeight.Bold)
            }
        }
        ServiceStatus.FINALIZADO -> {
            Text("Servicio Finalizado", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Calificar")
                }
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Pagar")
                }
            }
        }
    }
}
