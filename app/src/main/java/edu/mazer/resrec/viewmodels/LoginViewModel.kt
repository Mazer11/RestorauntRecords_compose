package edu.mazer.resrec.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import edu.mazer.resrec.model.AuthResult

class LoginViewModel : ViewModel() {

    private val myAuth = FirebaseAuth.getInstance()
    private val _authResult: MutableLiveData<AuthResult> = MutableLiveData()
    val authResult: LiveData<AuthResult> = _authResult

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ) {
        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onSuccess()
                _authResult.value = AuthResult.Successful
            } else {
                _authResult.value = AuthResult.Failure
            }
        }
    }

}