package com.example.opsc7311_poe_part2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.NumberPicker

class DGoals : AppCompatActivity() {
    //Home Button
    var buttonHome : ImageButton ?= null

    //Number Pickers
    lateinit var numPickHs : NumberPicker
    lateinit var numPickMs : NumberPicker
    val numPickFormatter : NumberPicker.Formatter = NumberPicker.Formatter { String.format("%02d", it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dgoals)

        buttonHome = findViewById(R.id.ib_dg_home)

        numPickHs = findViewById(R.id.numPickerHours)
        numPickHs.minValue = 0
        numPickHs.maxValue = 59
        numPickHs.setFormatter(numPickFormatter)

        numPickMs = findViewById(R.id.numPickerMinutes)
        numPickMs.minValue = 0
        numPickMs.maxValue = 59
        numPickMs.setFormatter(numPickFormatter)

        buttonHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}