package com.example.jetmv.ui.PRs.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

import androidx.lifecycle.LiveData
import com.example.jetmv.ui.components.SubmitButton

@Composable
fun PantallaPRs(viewModel: PRsVM = remember { PRsVM() }) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE3F2FD), // Color superior
                        Color(0xFF00FF00 )  // Color inferior
                    )
                )
            )
            .padding(16.dp),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Pantalla de PRs (Records Personales)")
        Spacer(modifier = Modifier.height(16.dp))
        PRItem("Bench Press", viewModel.benchPressPR) { pr ->
            viewModel.setBenchPressPR(pr)
        }
        Spacer(modifier = Modifier.height(8.dp))
        PRItem("Shoulder Press", viewModel.shoulderPressPR) { pr ->
            viewModel.setShoulderPressPR(pr)
        }
        Spacer(modifier = Modifier.height(8.dp))
        PRItem("Snatch", viewModel.snatchPR) { pr ->
            viewModel.setSnatchPR(pr)
        }
        Spacer(modifier = Modifier.height(8.dp))
        PRItem("Clean", viewModel.cleanPR) { pr ->
            viewModel.setCleanPR(pr)
        }
        Spacer(modifier = Modifier.height(8.dp))
        PRItem("Deadlift", viewModel.deadliftPR) { pr ->
            viewModel.setDeadliftPR(pr)
        }
        Spacer(modifier = Modifier.height(16.dp))
        // Success or failure message
        val message = viewModel.message.observeAsState("")
        Text(text = message.value)
        // Button to trigger PR submission
        SubmitButton(enabled = true, onClick = { viewModel.savePR() }, text = "Submit PRs", buttonColor = Color(0xFF52A64E))
    }
}

@Composable
fun PRItem(title: String, pr: LiveData<Float>, onPRChanged: (Float) -> Unit) {
    var prValue by remember { mutableStateOf("") }
    val currentPR by pr.observeAsState()

    OutlinedTextField(
        value = prValue,
        onValueChange = {
            prValue = it
            if (it.isNotEmpty()) {
                val newValue = it.toFloatOrNull() ?: 0f
                onPRChanged(newValue)
            }
        },
        label = { Text("PR de $title", style = TextStyle(
                fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
        ) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        modifier = Modifier.fillMaxWidth()
    )
    if (currentPR != null) {
        Text(text = "PR actual: $currentPR lbs")
    }
}