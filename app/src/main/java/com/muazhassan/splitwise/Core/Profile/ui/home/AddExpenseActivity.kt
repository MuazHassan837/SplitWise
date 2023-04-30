package com.muazhassan.splitwise.Core.Profile.ui.home

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.muazhassan.splitwise.R
import com.muazhassan.splitwise.databinding.ActivityAddExpenseBinding
import android.widget.SeekBar


class AddExpenseActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddExpenseBinding
    private var sliderVal : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)
        binding = ActivityAddExpenseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rootLayout = findViewById<View>(android.R.id.content)
        rootLayout.setBackgroundColor(Color.CYAN)

        binding.submitBtn.setOnClickListener {

            finish()
        }

        binding.slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}