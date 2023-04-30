package com.muazhassan.splitwise.Core.Profile

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider

import com.muazhassan.splitwise.Core.CoreVC
import com.muazhassan.splitwise.Core.Launch.MainFragment
import com.muazhassan.splitwise.Core.Login.LoggedInViewModel
import com.muazhassan.splitwise.R
import com.muazhassan.splitwise.databinding.ActivityProfileNavVcBinding
import com.muazhassan.splitwise.databinding.NavHeaderProfileNavVcBinding
import timber.log.Timber

class ProfileNavVC : CoreVC() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityProfileNavVcBinding
    private lateinit var navHeaderBinding: NavHeaderProfileNavVcBinding

    private val firebaseService by lazy {
        ViewModelProvider(this).get(LoggedInViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = ""


        binding = ActivityProfileNavVcBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarProfileNavVc.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_profile_nav_vc)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        var headerView = binding.navView.getHeaderView(0)
        navHeaderBinding = NavHeaderProfileNavVcBinding.bind(headerView)
        navHeaderBinding.navHeaderEmail.text = email

        navHeaderBinding.navHeaderImage.setOnClickListener {
            pickImage()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.profile_nav_v_c, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_profile_nav_vc)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun signOut() {
        val intent = Intent(this, MainFragment::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 222)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == 222) {
            val res = data?.data
            navHeaderBinding.navHeaderImage.setImageURI(res)
        }
    }



}