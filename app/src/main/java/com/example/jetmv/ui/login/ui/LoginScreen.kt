package com.example.jetmv.ui.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetmv.R
import com.example.jetmv.ui.components.EmailField
import com.example.jetmv.ui.components.LinkButton
import com.example.jetmv.ui.components.HeaderImage
import com.example.jetmv.ui.components.SubmitButton
import com.example.jetmv.ui.components.PasswordField
import kotlinx.coroutines.launch
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Login(navController, Modifier.align(Alignment.TopCenter), viewModel)
    }
}
@Composable
fun Login(navController: NavController, modifier: Modifier, viewModel: LoginViewModel) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")
    val loginEnable by viewModel.loginEnable.observeAsState(true)
    val isLoading by viewModel.isLoading.observeAsState(false)
    val coroutineScope = rememberCoroutineScope()

    if (isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
    } else {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(25.dp))
            HeaderImage(Modifier, painterResource(id = R.drawable.login1))
            Spacer(modifier = Modifier.padding(25.dp))
            EmailField(value = email, onValueChange = { viewModel.onLoginChanged(it, password) })
            Spacer(modifier = Modifier.padding(5.dp))
            PasswordField(value = password, onValueChange = { viewModel.onLoginChanged(email, it) })
            Spacer(modifier = Modifier.padding(8.dp))
            SubmitButton(enabled = loginEnable, onClick = {
                coroutineScope.launch {
                    viewModel.onLoginSelected()
                }
            }, text = "Iniciar Sesión")
            LinkButton(
                Modifier.align(Alignment.End),
                "Olvide mi contraseña",
                Color.Red,
                onClick = { navController.navigate("prs") })
            Spacer(modifier = Modifier.padding(5.dp))
            LinkButton(
                Modifier.align(Alignment.End),
                "No tengo una cuenta",
                Color.Blue,
                onClick = { navController.navigate("register") })
        }
    }
}
