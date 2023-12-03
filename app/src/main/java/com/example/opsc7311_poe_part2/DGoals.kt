package com.example.opsc7311_poe_part2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import com.bumptech.glide.Glide

class DGoals : AppCompatActivity() {
    //Home Button
    var buttonHome : ImageButton ?= null
    var btnMin : Button ?= null
    var btnMax : Button ?= null

    //Number Pickers
    lateinit var numPickHs : NumberPicker
    lateinit var numPickMs : NumberPicker
    val numPickFormatter : NumberPicker.Formatter = NumberPicker.Formatter { String.format("%02d", it) }

    //Text Display
    var mn : TextView ?= null
    var mx : TextView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dgoals)
        //set buttons
        buttonHome = findViewById(R.id.ib_dg_home)
        btnMin = findViewById(R.id.btn_set_min)
        btnMax = findViewById(R.id.btn_set_max)
        //set the pickers
        numPickHs = findViewById(R.id.numPickerHours)
        numPickHs.minValue = 0
        numPickHs.maxValue = 24
        numPickHs.setFormatter(numPickFormatter)

        numPickMs = findViewById(R.id.numPickerMinutes)
        numPickMs.minValue = 0
        numPickMs.maxValue = 59
        numPickMs.setFormatter(numPickFormatter)
        //set values for displaying
        var hours = 0
        var mins = 0

        val imageView = findViewById<ImageView>(R.id.ivImage)
        Glide.with(this).load(R.drawable.tokoloshiedailygoals).into(imageView)

        //set display texts


        numPickHs.setOnValueChangedListener{
            numberPicker, i, i2 -> hours = numberPicker.value
        }

        numPickMs.setOnValueChangedListener{
            numberPicker, i, i2 -> mins = numberPicker.value
        }

        btnMin?.setOnClickListener(){

        }

        btnMax?.setOnClickListener(){

        }

        buttonHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}