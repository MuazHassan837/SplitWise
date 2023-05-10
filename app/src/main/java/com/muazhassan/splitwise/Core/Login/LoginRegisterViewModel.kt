package com.muazhassan.splitwise.Core.Login

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.muazhassan.splitwise.Core.Service.FirebaseManager


class LoginRegisterViewModel (app: Application) : AndroidViewModel(app) {

    var firebaseManager : FirebaseManager = FirebaseManager(app)

    fun login(email: String?, password: String?,context: Context) {
        firebaseManager.login(email, password, context)
    }

    fun register(email: String?, password: String?,context: Context) {
        firebaseManager.register(email, password, context)
    }
}
