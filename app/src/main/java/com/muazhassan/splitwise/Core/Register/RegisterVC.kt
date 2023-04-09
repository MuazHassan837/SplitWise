package com.muazhassan.splitwise.Core.Register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.muazhassan.splitwise.Core.CoreVC
import com.muazhassan.splitwise.Core.Login.LoginRegisterViewModel
import com.muazhassan.splitwise.Core.Profile.ProfileNavVC
import com.muazhassan.splitwise.R
import com.muazhassan.splitwise.databinding.ActivityRegisterVcBinding
import timber.log.Timber

class RegisterVC : CoreVC() {
    private lateinit var storyboardBinding : ActivityRegisterVcBinding
    private lateinit var  viewModel : LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_vc)
        storyboardBinding = ActivityRegisterVcBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(LoginRegisterViewModel::class.java)
        setContentView(storyboardBinding.root)

        storyboardBinding.registerButton.setOnClickListener {
            val email = storyboardBinding.registerFieldEmail.text.toString()
            val password = storyboardBinding.registerFieldPassword.text.toString()
            val isValid = validateRegistrationFields(storyboardBinding.registerFieldEmail,
                storyboardBinding.registerFieldPassword)
            if (isValid) {
                Timber.i("Registration done: email=$email, password=$password")
            }
        }
    }


}