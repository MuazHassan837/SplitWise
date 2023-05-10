package com.muazhassan.splitwise.Core.Service

import android.app.Application
import android.content.Context
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muazhassan.splitwise.Core.Profile.ProfileNavVC
import timber.log.Timber

class FirebaseManager(application: Application) {

    private var application: Application? = null

    var firebaseAuth: FirebaseAuth? = null


    init {
        this.application = application
        firebaseAuth = FirebaseAuth.getInstance()
    }

    fun login(email: String?, password: String?, context: Context) {
        firebaseAuth!!.signInWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(application!!.mainExecutor) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(context, ProfileNavVC::class.java).apply {
                        putExtra("email", email)
                    }
                    context.startActivity(intent)
                } else {
                    val errorMessage = task.exception?.message
                    Timber.i("Login Failure: $errorMessage")
                }
            }
    }

    fun register(email: String?, password: String?, context: Context) {
        firebaseAuth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(application!!.mainExecutor) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(context, ProfileNavVC::class.java).apply {
                        putExtra("email", email)
                    }
                    context.startActivity(intent)
                } else {
                    Timber.i("Registration Failure: $task.exception!!.message")
                }
            }
    }


    fun logOut() {
        firebaseAuth!!.signOut()
    }
}
