import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterVM : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _isSubmitted = MutableLiveData(false)
    val isSubmitted: LiveData<Boolean> = _isSubmitted

    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = _registrationResult

    enum class RegistrationResult {
        SUCCESS,
        EMAIL_EXISTS,
        USERNAME_EXISTS,
        FAILURE
    }

    fun onEmailChanged(newEmail: String) {
        _email.value = newEmail
    }

    fun onUsernameChanged(newUsername: String) {
        _username.value = newUsername
    }

    fun onPasswordChanged(newPassword: String) {
        _password.value = newPassword
    }

    fun submit() {
        val email = _email.value ?: return
        val password = _password.value ?: return
        val username = _username.value ?: return

        // Check if email already exists
        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // Email already exists
                    _registrationResult.value = RegistrationResult.EMAIL_EXISTS
                } else {
                    // Check if username already exists
                    firestore.collection("users")
                        .whereEqualTo("username", username)
                        .get()
                        .addOnSuccessListener { documents ->
                            if (!documents.isEmpty) {
                                // Username already exists
                                _registrationResult.value = RegistrationResult.USERNAME_EXISTS
                            } else {
                                // Email and username do not exist, proceed with registration
                                auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            // Registration success
                                            val user = auth.currentUser
                                            val uid = user?.uid
                                            uid?.let {
                                                val userData = hashMapOf(
                                                    "email" to email,
                                                    "username" to username
                                                    // Add other user data fields as needed
                                                )
                                                firestore.collection("users").document(uid)
                                                    .set(userData)
                                                    .addOnSuccessListener {
                                                        _registrationResult.value =
                                                            RegistrationResult.SUCCESS
                                                    }
                                                    .addOnFailureListener {
                                                        _registrationResult.value =
                                                            RegistrationResult.FAILURE
                                                    }
                                            }
                                        } else {
                                            // Registration failed
                                            _registrationResult.value = RegistrationResult.FAILURE
                                        }
                                    }
                            }
                        }
                        .addOnFailureListener {
                            // Error occurred while checking username
                            _registrationResult.value = RegistrationResult.FAILURE
                        }
                }
            }
            .addOnFailureListener {
                // Error occurred while checking email
                _registrationResult.value = RegistrationResult.FAILURE
            }
    }
}