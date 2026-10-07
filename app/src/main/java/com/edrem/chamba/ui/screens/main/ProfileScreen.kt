package com.edrem.chamba.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.edrem.chamba.ui.components.WalletDashboard
import com.edrem.chamba.ui.theme.ActionOrange
import com.edrem.chamba.ui.theme.TrustBlue

@Composable
fun ProfileScreen(isChambaMode: Boolean, onModeToggle: (Boolean) -> Unit) {
    val bgColor = if (isChambaMode) ActionOrange.copy(alpha = 0.05f) else MaterialTheme.colorScheme.background
    val primaryColor = if (isChambaMode) ActionOrange else TrustBlue

    LazyColumn(modifier = Modifier.fillMaxSize().background(bgColor).padding(horizontal = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        item { Spacer(modifier = Modifier.height(48.dp)) }
        item {
            Box(modifier = Modifier.size(100.dp).clip(CircleShape).background(primaryColor.copy(alpha = 0.2f)), contentAlignment = Alignment.Center) {
                Text("JM", style = MaterialTheme.typography.headlineLarge, color = primaryColor)
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Juan Pérez", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.VerifiedUser, contentDescription = "DNI Verified", tint = primaryColor)
            }
        }
        item {
            Text("DNI Verificado", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(20.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(if (isChambaMode) "Modo Chamba Activado" else "Activar Modo Chamba", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Text(if (isChambaMode) "Recibiendo solicitudes" else "Busca profesionales", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = isChambaMode, onCheckedChange = { onModeToggle(it) }, colors = SwitchDefaults.colors(checkedThumbColor = ActionOrange))
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }

        if (isChambaMode) {
            item {
                WalletDashboard()
                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        item {
            val options = listOf("Mis favoritos", "Métodos de pago", "Soporte 24/7", "Configuración")
            options.forEach { option ->
                Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(option, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
