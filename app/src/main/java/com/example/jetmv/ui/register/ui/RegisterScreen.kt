package com.example.jetmv.ui.register.ui

import RegisterVM
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetmv.ui.components.EmailField
import com.example.jetmv.ui.components.UsernameField
import com.example.jetmv.ui.components.PasswordField
import com.example.jetmv.ui.components.SubmitButton

@Composable
fun RegisterScreen(viewModel: RegisterVM = viewModel()) {
    val email: String by viewModel.email.observeAsState("")
    val username: String by viewModel.username.observeAsState("")
    val password: String by viewModel.password.observeAsState("")
    val isSubmitted: Boolean by viewModel.isSubmitted.observeAsState(false)
    val registrationResult: RegisterVM.RegistrationResult? by viewModel.registrationResult.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register")
        Spacer(modifier = Modifier.height(16.dp))
        EmailField(value = email, onValueChange = viewModel::onEmailChanged)
        Spacer(modifier = Modifier.height(8.dp))
        UsernameField(value = username, onValueChange = viewModel::onUsernameChanged)
        Spacer(modifier = Modifier.height(8.dp))
        PasswordField(
            value = password,
            onValueChange = viewModel::onPasswordChanged,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        SubmitButton(enabled = true, onClick = viewModel::submit, text = "Submit")
        Spacer(modifier = Modifier.height(16.dp))
        when (registrationResult) {
            RegisterVM.RegistrationResult.SUCCESS -> {
                Text("Registration submitted successfully!")
            }
            RegisterVM.RegistrationResult.EMAIL_EXISTS -> {
                Text("Email already exists!")
            }
            RegisterVM.RegistrationResult.USERNAME_EXISTS -> {
                Text("Username already exists!")
            }
            RegisterVM.RegistrationResult.FAILURE -> {
                Text("Registration failed. Please try again.")
            }
            null -> {
                // No action needed
            }
        }
    }
}