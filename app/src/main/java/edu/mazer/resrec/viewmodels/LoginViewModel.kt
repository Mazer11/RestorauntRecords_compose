package edu.mazer.resrec.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import edu.mazer.resrec.model.AuthResult
import edu.mazer.resrec.navigation.NavigationRoutes

class LoginViewModel : ViewModel() {

    val myAuth = FirebaseAuth.getInstance()
    val TAG = "login_view_model"

    private val _authResult: MutableLiveData<AuthResult> = MutableLiveData()
    val authResult: LiveData<AuthResult> = _authResult

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit
    ){
        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                onSuccess()
                _authResult.value = AuthResult.Succesfull
            } else {
                _authResult.value = AuthResult.Failure
            }
        }
    }

}