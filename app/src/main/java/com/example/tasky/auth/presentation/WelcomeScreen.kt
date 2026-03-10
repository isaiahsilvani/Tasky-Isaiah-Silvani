package com.example.tasky.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.tasky.ui.theme.LinkPurple
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph

@Composable
@Destination<RootGraph>(start = true)
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    viewModel: WelcomeScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val isLogin = state.toggle == WelcomeToggle.LOGIN

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = if (isLogin) "Welcome back!" else "Create your account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 20.dp, vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (!isLogin) {
                TaskyInput(
                    value = state.name,
                    label = "Name",
                    onValueChange = { viewModel.onEvent(WelcomeEvent.OnNameChange(it)) }
                )
            }

            TaskyInput(
                value = state.email,
                label = "Email",
                onValueChange = { viewModel.onEvent(WelcomeEvent.OnEmailChange(it)) }
            )

            TaskyInput(
                value = state.password,
                label = "Password",
                onValueChange = { viewModel.onEvent(WelcomeEvent.OnPasswordChange(it)) }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // Todo - implement login/register click logic
                },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(
                    text = if (isLogin) "LOG IN" else "GET STARTED",
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            AuthFooter(
                isLogin = isLogin,
                onToggle = {
                    if (isLogin) viewModel.onEvent(WelcomeEvent.OnRegisterClick)
                    else viewModel.onEvent(WelcomeEvent.OnLoginClick)
                }
            )
        }
    }
}

@Composable
fun TaskyInput(
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun AuthFooter(
    isLogin: Boolean,
    onToggle: () -> Unit
) {
    val footerText = buildAnnotatedString {
        val description = if (isLogin) "DON'T HAVE AN ACCOUNT? " else "ALREADY HAVE AN ACCOUNT? "
        val actionText = if (isLogin) "SIGN UP" else "LOG IN"

        // Description text using surfaceVariant (#76808F)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) {
            append(description)
        }

        // Action link setup
        val link = LinkAnnotation.Clickable(
            tag = "auth_toggle",
            linkInteractionListener = { onToggle() }
        )

        withLink(link) {
            withStyle(
                style = SpanStyle(
                    color = LinkPurple,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.None
                )
            ) {
                append(actionText)
            }
        }
    }

    Text(text = footerText)
}
