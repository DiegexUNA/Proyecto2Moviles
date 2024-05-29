package com.example.jetmv.ui.login.ui

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.*
import kotlinx.coroutines.delay

class LoginViewModel: ViewModel() {
    private val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _loginEnable = MutableLiveData<Boolean>()
    val loginEnable : LiveData<Boolean> = _loginEnable

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : MutableLiveData<Boolean> = _isLoading

    fun onLoginChanged(email:String, password: String){
        _email.value = email
        _password.value = password
        _loginEnable.value = isValidEmail(email) && isValidPassword(password)
    }

    private fun isValidEmail(email: String):Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidPassword(password: String):Boolean{
        //aqui van las validaciones con Regex
        // se verifican normalmente largo
        //combinacion de mayusculas, minuscuals , numeros
        //caracteres especiales y el largo que ya tenemos
        return password.length > 6
    }

    suspend fun  onLoginSelected(){
        isLoading.value = true
        delay(4000)
        isLoading.value = false
    }

}


