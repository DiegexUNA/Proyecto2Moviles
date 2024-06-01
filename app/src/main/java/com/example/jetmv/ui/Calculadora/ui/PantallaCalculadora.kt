package com.example.jetmv.ui.Calculadora.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow


@Composable
fun PantallaCalculadora() {
    val viewModel: CalculadoraVM = viewModel()
    val inputWeight by viewModel.inputWeight.observeAsState("")
    val calculatedWeights by viewModel.calculatedWeights.observeAsState(emptyMap())
    var inputError by remember { mutableStateOf(false) }
    var showWeights by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla Calculadora de Pesos")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputWeight,
            onValueChange = { viewModel.onInputWeightChanged(it) },
            label = { Text("Ingrese el peso total en lbs") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )
        if (inputError) {
            Text(
                text = "El peso debe ser igual o mayor a 45 lbs y no puede estar vacío",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                inputError = (inputWeight.toDoubleOrNull() ?: 0.0) < 45.0 || inputWeight.isEmpty()
                showWeights = if (!inputError) {
                    viewModel.calculateWeights()
                    true
                } else {
                    false
                }
            },
        ) {
            Text("Calcular")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (showWeights) {
            Text("Barra: 45 lbs")
            calculatedWeights.forEach { (weight, count) ->
                Text("Discos de $weight lbs: $count")
            }
            val maxWeight = calculatedWeights.keys.sumOf { it * calculatedWeights[it]!! } + 45
            Text("Peso máximo posible: $maxWeight lbs")
        }
    }
}

