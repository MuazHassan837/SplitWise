package com.muazhassan.splitwise.Core.Profile.ui.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.muazhassan.splitwise.Model.SplitStrategy
import com.muazhassan.splitwise.R
import com.muazhassan.splitwise.databinding.ActivityAddExpenseBinding
import timber.log.Timber
import android.app.Activity
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
            val amount = binding.amount.text.toString().toDoubleOrNull()

            val splitStrategy = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.equally  -> SplitStrategy.EQUAL
                R.id.exactAmounts -> SplitStrategy.EQUAL_MINUS
                R.id.percentageSplit -> SplitStrategy.PERCENTAGE
                else -> null
            }

            val desp = binding.description.text.toString()

            val data = Bundle().apply {
                putDouble("amount", amount ?: 0.0)
                putSerializable("splitStrategy", splitStrategy)
                putString("description", desp)
                putInt("sliderValue", sliderVal)
            }

            setResult(Activity.RESULT_OK, Intent().putExtras(data))
            finish()
        }

        binding.slider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                sliderVal = progress
                binding.SplitPercent.text = "${progress}%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}