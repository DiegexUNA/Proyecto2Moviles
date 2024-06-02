package com.example.jetmv.ui.PRs.ui

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

import androidx.lifecycle.LiveData

@Composable
fun PantallaPRs(viewModel: PRsViewModel = remember { PRsViewModel() }) {
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
        label = { Text("PR de $title") },
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
