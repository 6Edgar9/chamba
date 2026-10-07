package com.edrem.chamba

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.edrem.chamba.ui.navigation.AuthScreen
import com.edrem.chamba.ui.navigation.MainTab
import com.edrem.chamba.ui.screens.auth.LoginScreen
import com.edrem.chamba.ui.screens.auth.OnboardingScreen
import com.edrem.chamba.ui.screens.main.*
import com.edrem.chamba.ui.theme.ChambaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChambaTheme {
                ChambaApp()
            }
        }
    }
}

@Composable
fun ChambaApp() {
    var isLoggedIn by remember { mutableStateOf(false) }
    var currentAuthScreen by remember { mutableStateOf<AuthScreen>(AuthScreen.Onboarding) }
    
    var isChambaMode by remember { mutableStateOf(false) }
    var currentMainTab by remember { mutableStateOf<MainTab>(MainTab.HomeOrDashboard) }
    
    var selectedChatId by remember { mutableStateOf<String?>(null) }
    var showActiveService by remember { mutableStateOf(false) }

    if (!isLoggedIn) {
        when (currentAuthScreen) {
            AuthScreen.Onboarding -> OnboardingScreen(onStartClick = { currentAuthScreen = AuthScreen.Login })
            AuthScreen.Login -> LoginScreen(onLoginSuccess = { isLoggedIn = true })
        }
    } else {
        if (selectedChatId != null) {
            ChatDetailScreen(chatId = selectedChatId!!, onBack = { selectedChatId = null })
        } else if (showActiveService) {
            ActiveServiceScreen(isChambaMode = isChambaMode, onBack = { showActiveService = false })
        } else {
            Scaffold(
                bottomBar = {
                    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                        NavigationBarItem(
                            icon = { Icon(if (isChambaMode) Icons.Default.Work else Icons.Default.Map, contentDescription = null) },
                            label = { Text(if (isChambaMode) "Panel" else "Mapa") },
                            selected = currentMainTab == MainTab.HomeOrDashboard,
                            onClick = { currentMainTab = MainTab.HomeOrDashboard }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.List, contentDescription = null) },
                            label = { Text("Actividad") },
                            selected = currentMainTab == MainTab.Activities,
                            onClick = { currentMainTab = MainTab.Activities }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Chat, contentDescription = null) },
                            label = { Text("Chat") },
                            selected = currentMainTab == MainTab.Chat,
                            onClick = { currentMainTab = MainTab.Chat }
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Person, contentDescription = null) },
                            label = { Text("Perfil") },
                            selected = currentMainTab == MainTab.Profile,
                            onClick = { currentMainTab = MainTab.Profile }
                        )
                    }
                }
            ) { paddingValues ->
                Surface(modifier = Modifier.padding(paddingValues), color = MaterialTheme.colorScheme.background) {
                    when (currentMainTab) {
                        MainTab.HomeOrDashboard -> {
                            if (isChambaMode) ChambaDashboardScreen() else HomeScreen()
                        }
                        MainTab.Activities -> {
                            Column {
                                Button(onClick = { showActiveService = true }, modifier = Modifier.padding(16.dp).fillMaxWidth()) {
                                    Text("Simular Servicio Activo (Ir a pantalla)")
                                }
                                ActivitiesScreen(isChambaMode)
                            }
                        }
                        MainTab.Chat -> ChatListScreen(onChatSelected = { selectedChatId = it })
                        MainTab.Profile -> ProfileScreen(isChambaMode = isChambaMode, onModeToggle = { isChambaMode = it })
                    }
                }
            }
        }
    }
}