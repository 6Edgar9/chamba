package com.edrem.chamba.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.edrem.chamba.ui.models.MockData

@Composable
fun ChatListScreen(onChatSelected: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Mensajes", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 24.dp))
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(MockData.chats) { chat ->
                Row(
                    modifier = Modifier.fillMaxWidth().clickable { onChatSelected(chat.id) }.padding(horizontal = 24.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.size(50.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primaryContainer), contentAlignment = Alignment.Center) {
                        Text(chat.contactName.take(1), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(chat.contactName, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                        Text(chat.lastMessage, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(chatId: String, onBack: () -> Unit) {
    val chat = MockData.chats.find { it.id == chatId }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(chat?.contactName ?: "Chat") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier.weight(1f), placeholder = { Text("Escribe un mensaje...") }, shape = RoundedCornerShape(24.dp))
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = {}, modifier = Modifier.background(MaterialTheme.colorScheme.primary, CircleShape)) {
                    Icon(Icons.Default.Send, contentDescription = "Enviar", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(paddingValues), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(MockData.messages) { msg ->
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (msg.isMine) Arrangement.End else Arrangement.Start) {
                    Box(modifier = Modifier.background(if (msg.isMine) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)).padding(16.dp)) {
                        Text(msg.text, color = if (msg.isMine) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
}
