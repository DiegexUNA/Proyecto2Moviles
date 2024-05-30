package com.example.jetmv.ui.Entrenamientos.ui

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.LocalDate
import java.util.*
import android.content.Context

fun ShowDatePickerDialog(context: Context, onDateSelected: (LocalDate) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected(LocalDate.of(selectedYear, selectedMonth + 1, selectedDay))
        },
        year,
        month,
        day
    ).show()
}

@Composable
fun PantallaEntrenamientos(viewModel: EntrenamientosVM = viewModel()) {
    var fechaSeleccionada by remember { mutableStateOf<LocalDate?>(null) }
    val ejercicios by viewModel.ejercicios.collectAsState()
    val context = LocalContext.current

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                ShowDatePickerDialog(context) { selectedDate ->
                    fechaSeleccionada = selectedDate
                    viewModel.obtenerEjerciciosPorFecha(selectedDate)
                }
            }) {
                Text(text = "Seleccionar Fecha")
            }

            fechaSeleccionada?.let {
                Text(text = "Fecha seleccionada: $it")
            }

            Spacer(modifier = Modifier.height(16.dp))

            ejercicios.forEach { ejercicio ->
                Text(text = "${ejercicio.nombre}: ${ejercicio.descripcion}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}