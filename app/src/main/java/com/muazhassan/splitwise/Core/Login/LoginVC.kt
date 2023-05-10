package com.muazhassan.splitwise.Core.Login

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.muazhassan.splitwise.Core.CoreVC
import com.muazhassan.splitwise.Core.Profile.ProfileNavVC
import com.muazhassan.splitwise.R
import com.muazhassan.splitwise.databinding.ActivityLoginVcBinding
import timber.log.Timber

class LoginVC : CoreVC() {
    private lateinit var storyboardBinding : ActivityLoginVcBinding
    private lateinit var  viewModel : LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_vc)
        storyboardBinding = ActivityLoginVcBinding.inflate(layoutInflater)
        setContentView(storyboardBinding.root)
        viewModel = ViewModelProvider(this).get(LoginRegisterViewModel::class.java)


        storyboardBinding.loginButton.setOnClickListener {
            val email = storyboardBinding.loginFieldEmail.text.toString()
            val password = storyboardBinding.loginFieldPassword.text.toString()
            val isValid = validateRegistrationFields(storyboardBinding.loginFieldEmail,
                storyboardBinding.loginFieldPassword)
            if (isValid) {
                Timber.i("login done: email=$email, password=$password")
                viewModel.login(email,password, this)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        if (FirebaseAuth.getInstance().currentUser?.email != null) {
            startActivity(Intent(this, ProfileNavVC::class.java))
        }
    }

}