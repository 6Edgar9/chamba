package com.edrem.chamba.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.edrem.chamba.ui.components.RequestCard
import com.edrem.chamba.ui.components.ReviewModal
import com.edrem.chamba.ui.models.MockData

@Composable
fun ActivitiesScreen(isChambaMode: Boolean = false) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Pendientes", "En proceso", "Completadas")
    
    var showReviewModal by remember { mutableStateOf(false) }
    var selectedProName by remember { mutableStateOf("") }

    if (showReviewModal) {
        ReviewModal(
            proName = selectedProName,
            onDismiss = { showReviewModal = false },
            onSubmit = { rating, tags -> 
                showReviewModal = false
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Spacer(modifier = Modifier.height(24.dp))
        Text("Actividad", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 24.dp))
        Spacer(modifier = Modifier.height(16.dp))
        TabRow(selectedTabIndex = selectedTabIndex, containerColor = MaterialTheme.colorScheme.background, contentColor = MaterialTheme.colorScheme.primary) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTabIndex == index, onClick = { selectedTabIndex = index }, text = { Text(title, fontWeight = FontWeight.Medium) })
            }
        }
        val filteredRequests = MockData.clientRequests.filter { it.status == tabs[selectedTabIndex] }
        LazyColumn(contentPadding = PaddingValues(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(filteredRequests) { req -> 
                RequestCard(
                    request = req,
                    onReviewClick = { 
                        selectedProName = req.assignedProName ?: "Profesional"
                        showReviewModal = true
                    }
                ) 
            }
        }
    }
}
