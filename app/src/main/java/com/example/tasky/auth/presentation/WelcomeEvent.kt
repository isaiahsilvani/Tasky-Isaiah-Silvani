package com.example.tasky.auth.presentation

sealed class WelcomeEvent {

    data class OnNameChange(val name: String): WelcomeEvent()
    data class OnEmailChange(val email: String): WelcomeEvent()
    data class OnPasswordChange(val password: String): WelcomeEvent()

    object OnLoginClick: WelcomeEvent()
    object OnRegisterClick: WelcomeEvent()
}
