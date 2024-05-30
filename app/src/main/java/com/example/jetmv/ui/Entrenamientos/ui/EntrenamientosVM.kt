package com.example.jetmv.ui.Entrenamientos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

data class Ejercicio(val nombre: String, val descripcion: String)

class EntrenamientosVM : ViewModel() {

    private val _ejercicios = MutableStateFlow<List<Ejercicio>>(emptyList())
    val ejercicios: StateFlow<List<Ejercicio>> = _ejercicios

    fun obtenerEjerciciosPorFecha(fecha: LocalDate) {
        // Simulación de la recuperación de datos. En una implementación real, se haría una llamada a una base de datos o API.
        val ejercicios = List(20) {
            Ejercicio(nombre = "Ejercicio ${it + 1}", descripcion = "Descripción del ejercicio ${it + 1}")
        }
        _ejercicios.value = ejercicios
    }
}