package edu.mazer.resrec.model

sealed class AuthResult(val value: Int){
    object Successful: AuthResult(1)
    object Failure: AuthResult(0)
}
