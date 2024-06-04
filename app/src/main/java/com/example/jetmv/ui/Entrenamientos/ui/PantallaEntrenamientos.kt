package com.example.jetmv.ui.Entrenamientos.ui

import EntrenamientosVM
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
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

fun ShowDatePickerDialog(context: Context, onDateSelected: (LocalDate) -> Unit) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected(LocalDate.of(selectedYear, selectedMonth + 1, selectedDay))
        },
        year,
        month,
        day
    )
    datePickerDialog.datePicker.minDate = calendar.timeInMillis
    datePickerDialog.show()
}

@Composable
fun PantallaEntrenamientos(viewModel: EntrenamientosVM = viewModel()) {
    var fechaSeleccionada by remember { mutableStateOf<LocalDate?>(null) }
    val ejercicios by viewModel.ejercicios.collectAsState()
    val context = LocalContext.current

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
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                ShowDatePickerDialog(context) { selectedDate ->
                    fechaSeleccionada = selectedDate
                    viewModel.obtenerEjerciciosPorFecha(selectedDate)
                }
            }) {
                Text(text = "Seleccionar Fecha", textAlign = TextAlign.Center)
            }

            fechaSeleccionada?.let {
                Text(text = "Fecha seleccionada: $it", textAlign = TextAlign.Center)
            }

            Spacer(modifier = Modifier.height(16.dp))

            ejercicios.groupBy { it.hora }.forEach { (hora, ejerciciosPorHora) ->
                Text(
                    text = "${hora}: ${ejerciciosPorHora.joinToString { ejercicio -> ejercicio.nombre }}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}
