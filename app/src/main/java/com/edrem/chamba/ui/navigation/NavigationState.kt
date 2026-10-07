package com.edrem.chamba.ui.navigation

sealed class AuthScreen {
    object Onboarding : AuthScreen()
    object Login : AuthScreen()
}

sealed class MainTab {
    object HomeOrDashboard : MainTab()
    object Activities : MainTab()
    object Chat : MainTab()
    object Profile : MainTab()
}
