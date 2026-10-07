package com.edrem.chamba.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.edrem.chamba.ui.theme.ActionOrange
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var dni by remember { mutableStateOf("") }
    var showFaceScanner by remember { mutableStateOf(false) }
    var isScanning by remember { mutableStateOf(false) }

    if (showFaceScanner) {
        AlertDialog(
            onDismissRequest = { showFaceScanner = false },
            title = { Text("Escaneo Facial Activo") },
            text = {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(if (isScanning) ActionOrange.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(60.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Face, contentDescription = null, modifier = Modifier.size(64.dp), tint = if (isScanning) ActionOrange else MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(if (isScanning) "Identificando..." else "Posiciona tu rostro")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isScanning = true
                    }
                ) {
                    Text("Escanear")
                }
            }
        )

        LaunchedEffect(isScanning) {
            if (isScanning) {
                delay(1500) // Simulating API call
                onLoginSuccess()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Ingresa tu DNI para continuar", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        
        Spacer(modifier = Modifier.height(48.dp))
        
        OutlinedTextField(
            value = dni,
            onValueChange = { dni = it },
            label = { Text("Documento de Identidad (DNI)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = onLoginSuccess,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Ingresar como Cliente", style = MaterialTheme.typography.titleMedium)
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = { showFaceScanner = true },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = ActionOrange)
        ) {
            Icon(Icons.Default.Face, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Soy Profesional: Escaneo Facial", style = MaterialTheme.typography.titleMedium)
        }
    }
}
