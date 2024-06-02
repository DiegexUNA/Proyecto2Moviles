package com.example.jetmv.ui.PRs.ui

// PRsViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PRsViewModel : ViewModel() {
    private val _benchPressPR = MutableLiveData<Float>()
    val benchPressPR: LiveData<Float> = _benchPressPR

    private val _shoulderPressPR = MutableLiveData<Float>()
    val shoulderPressPR: LiveData<Float> = _shoulderPressPR

    private val _snatchPR = MutableLiveData<Float>()
    val snatchPR: LiveData<Float> = _snatchPR

    private val _cleanPR = MutableLiveData<Float>()
    val cleanPR: LiveData<Float> = _cleanPR

    private val _deadliftPR = MutableLiveData<Float>()
    val deadliftPR: LiveData<Float> = _deadliftPR

    fun setBenchPressPR(pr: Float) {
        _benchPressPR.value = pr
    }

    fun setShoulderPressPR(pr: Float) {
        _shoulderPressPR.value = pr
    }

    fun setSnatchPR(pr: Float) {
        _snatchPR.value = pr
    }

    fun setCleanPR(pr: Float) {
        _cleanPR.value = pr
    }

    fun setDeadliftPR(pr: Float) {
        _deadliftPR.value = pr
    }
}
