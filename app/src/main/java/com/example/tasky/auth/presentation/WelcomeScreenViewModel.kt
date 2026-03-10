package com.example.tasky.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeScreenViewModel @Inject constructor(): ViewModel() {

    var state by mutableStateOf(WelcomeScreenState())

    fun onEvent(event: WelcomeEvent) {
        state = when (event) {
            is WelcomeEvent.OnNameChange -> state.copy(name = event.name)
            is WelcomeEvent.OnEmailChange -> state.copy(email = event.email)
            is WelcomeEvent.OnPasswordChange -> state.copy(password = event.password)

            is WelcomeEvent.OnLoginClick -> state.resetFor(WelcomeToggle.LOGIN)
            is WelcomeEvent.OnRegisterClick -> state.resetFor(WelcomeToggle.REGISTER)
        }
    }

    private fun WelcomeScreenState.resetFor(toggle: WelcomeToggle) = copy(
        toggle = toggle,
        email = "",
        name = "",
        password = ""
    )
}
