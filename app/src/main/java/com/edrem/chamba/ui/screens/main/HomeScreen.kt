package com.edrem.chamba.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.edrem.chamba.ui.components.CategoryChip
import com.edrem.chamba.ui.components.ProfessionalCard
import com.edrem.chamba.ui.components.SearchBar
import com.edrem.chamba.ui.models.MockData
import com.edrem.chamba.ui.theme.TrustBlueLight
import com.edrem.chamba.ui.theme.UrgentRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 350.dp,
        sheetContainerColor = MaterialTheme.colorScheme.background,
        sheetShape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        sheetContent = {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(8.dp))
                Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                    SearchBar(query = "", onQueryChange = {})
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = UrgentRed),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("🚨 Urgente 24/7", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Categorías", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                LazyRow(contentPadding = PaddingValues(horizontal = 24.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(MockData.categories) { category -> CategoryChip(category) }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("Profesionales cerca de ti", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 24.dp))
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 100.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    items(MockData.professionals) { pro -> ProfessionalCard(pro) }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(TrustBlueLight)
        ) {
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = UrgentRed, modifier = Modifier.align(Alignment.Center).size(48.dp).offset(y = (-50).dp))
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.align(Alignment.TopStart).offset(x = 100.dp, y = 150.dp).size(36.dp))
            Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.align(Alignment.TopEnd).offset(x = (-80).dp, y = 200.dp).size(36.dp))
            
            Text(
                "Mapa Interactivo\n(Desliza la hoja inferior hacia arriba y abajo)",
                modifier = Modifier.align(Alignment.TopCenter).padding(top = 48.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
