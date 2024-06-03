import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterVM : ViewModel() {
    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _username = MutableLiveData("")
    val username: LiveData<String> = _username

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    private val _isSubmitted = MutableLiveData(false)
    val isSubmitted: LiveData<Boolean> = _isSubmitted

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
        // Add validation or submission logic here
        _isSubmitted.value = true
    }
}

