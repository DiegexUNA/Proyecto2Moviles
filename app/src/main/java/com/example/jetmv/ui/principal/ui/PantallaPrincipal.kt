package com.example.jetmv.ui.principal.ui
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetmv.R
import com.example.jetmv.ui.login.ui.LoginViewModel

@Composable
fun PantallaPrincipal(navController: NavController, viewModel: PantallaPrincipalVM, loginViewModel: LoginViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD), // Color superior
                        Color(0xFF572364 )  // Color inferior
                    )
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "GymDesUnidos",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("prs") }) {
                Text(text = "Personal Records PRs")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("calculadora") }) {
                Text(text = "Calculadora de Pesos")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("entrenamientos") }) {
                Text(text = "Entrenamientos")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                loginViewModel.logout()
                navController.navigate("login") {
                    popUpTo("main") { inclusive = true }
                }
            }) {
                Text("Logout")
            }
        }
    }
}