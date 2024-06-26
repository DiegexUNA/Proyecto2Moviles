package com.example.jetmv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.jetmv.ui.Calculadora.ui.PantallaCalculadora
import com.example.jetmv.ui.Entrenamientos.ui.PantallaEntrenamientos
import com.example.jetmv.ui.PRs.ui.PantallaPRs
import com.example.jetmv.ui.login.ui.LoginScreen
import com.example.jetmv.ui.login.ui.LoginViewModel
import com.example.jetmv.ui.principal.ui.PantallaPrincipalVM
import com.example.jetmv.ui.principal.ui.PantallaPrincipal
import com.example.jetmv.ui.register.ui.RegisterScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val loginViewModel = remember { LoginViewModel() }
            val mainViewModel = remember { PantallaPrincipalVM() }

            NavHost(navController, startDestination = "login") {
                composable("login") {
                    val loginSuccessful by loginViewModel.loginSuccessful.observeAsState()

                    if (loginSuccessful == true) {
                        LaunchedEffect(Unit) {
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }

                    LoginScreen(navController, loginViewModel)
                }
                composable("main") {
                    PantallaPrincipal(navController, mainViewModel, loginViewModel)
                }
                composable("prs") {
                    PantallaPRs()
                }
                composable("register") {
                    RegisterScreen()
                }
                composable("calculadora") {
                    PantallaCalculadora()
                }
                composable("entrenamientos") {
                    PantallaEntrenamientos()
                }
                composable("login2") {
                    LoginScreen(navController, loginViewModel)
                }
            }
        }
    }
}


