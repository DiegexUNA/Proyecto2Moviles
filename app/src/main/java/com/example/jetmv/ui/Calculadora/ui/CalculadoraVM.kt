package com.example.jetmv.ui.Calculadora.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalculadoraVM : ViewModel() {
    private val _barWeight = 45
    private val _weights = listOf(45.0, 35.0, 25.0, 15.0, 10.0, 5.0, 2.5)

    private val _inputWeight = MutableLiveData("")
    val inputWeight: LiveData<String> = _inputWeight

    private val _calculatedWeights = MutableLiveData<Map<Double, Int>>()
    val calculatedWeights: LiveData<Map<Double, Int>> = _calculatedWeights

    fun onInputWeightChanged(weight: String) {
        _inputWeight.value = weight
    }

    fun calculateWeights() {
        val totalWeight = _inputWeight.value?.toDoubleOrNull() ?: return
        val weightToDistribute = totalWeight - _barWeight
        val result = mutableMapOf<Double, Int>()

        var remainingWeight = weightToDistribute / 2  // Discos en múltiplos de 2
        for (weight in _weights) {
            val count = (remainingWeight / weight ).toInt()
            if (count > 0) {
                result[weight] = count * 2
                remainingWeight -= count * weight
            }
        }
        _calculatedWeights.value = result
    }
}