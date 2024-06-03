package com.example.jetmv.ui.PRs.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PRsVM : ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message
    private val db = FirebaseFirestore.getInstance()

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

    private val auth = FirebaseAuth.getInstance()
    private val currentUserUid: String? = auth.currentUser?.uid

    init {
        loadPRs()
    }

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

    fun savePR() {
        currentUserUid?.let { uid ->
            val userPrsCollection = db.collection("users").document(uid).collection("prs")

            val prList = listOf(
                "Bench Press" to benchPressPR.value,
                "Shoulder Press" to shoulderPressPR.value,
                "Snatch" to snatchPR.value,
                "Clean" to cleanPR.value,
                "Deadlift" to deadliftPR.value
            )

            prList.forEach { (exercise, pr) ->
                pr?.let {
                    userPrsCollection.document(exercise).set(mapOf("pr" to pr.toDouble()))
                        .addOnSuccessListener {
                            setMessage("PR for $exercise saved successfully")
                        }
                        .addOnFailureListener { e ->
                            setMessage("Error saving PR for $exercise: ${e.message}")
                        }
                }
            }
        } ?: run {
            setMessage("Current user UID not available")
        }
    }

    fun loadPRs() {
        currentUserUid?.let { uid ->
            val userPrsCollection = db.collection("users").document(uid).collection("prs")

            userPrsCollection.get()
                .addOnSuccessListener { querySnapshot ->
                    for (document in querySnapshot.documents) {
                        val exercise = document.id
                        val prValue = document.getDouble("pr")
                        when (exercise) {
                            "Bench Press" -> _benchPressPR.value = prValue?.toFloat() ?: 0f
                            "Shoulder Press" -> _shoulderPressPR.value = prValue?.toFloat() ?: 0f
                            "Snatch" -> _snatchPR.value = prValue?.toFloat() ?: 0f
                            "Clean" -> _cleanPR.value = prValue?.toFloat() ?: 0f
                            "Deadlift" -> _deadliftPR.value = prValue?.toFloat() ?: 0f
                        }
                    }
                    setMessage("PRs loaded successfully")
                }
                .addOnFailureListener { e ->
                    setMessage("Error loading PRs: ${e.message}")
                }
        } ?: run {
            setMessage("Current user UID not available")
        }
    }

    fun setMessage(msg: String) {
        _message.value = msg
    }
}