package com.muazhassan.splitwise.Core.Launch

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.muazhassan.splitwise.Core.CoreVC
import com.muazhassan.splitwise.Core.Login.LoginVC
import com.muazhassan.splitwise.Core.Register.RegisterVC
import com.muazhassan.splitwise.databinding.FragmentMainBinding
import timber.log.Timber

class MainFragment : CoreVC() {

    private lateinit var storyboardBinding : FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        storyboardBinding = FragmentMainBinding.inflate(layoutInflater)
        setContentView(storyboardBinding.root)


        storyboardBinding.registerLauncher.setOnClickListener {
            val intent = Intent(this, RegisterVC::class.java)
            startActivity(intent)
        }

        storyboardBinding.logInLauncher.setOnClickListener {
            val intent = Intent(this, LoginVC::class.java)
            startActivity(intent)
        }

    }

}