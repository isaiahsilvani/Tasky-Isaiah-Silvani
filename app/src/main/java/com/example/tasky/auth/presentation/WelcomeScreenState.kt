package com.example.tasky.auth.presentation

data class WelcomeScreenState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val toggle: WelcomeToggle = WelcomeToggle.LOGIN
)
