package com.example.opsc7311_poe_part2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.opsc7311_poe_part2.model.MinMaxGoals
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

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

        //db
        val database = Firebase.database
        val dbReference = database.getReference("DailyGoal")


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

            val orders = MinMaxGoals(hours, mins)

            //dbReference.push().setValue(orders)
            val pushRef = dbReference.push()
            pushRef.setValue(orders, object :DatabaseReference.CompletionListener
            {
                override fun onComplete(error: DatabaseError?, ref: DatabaseReference)
                {
                    if(error == null)
                    {
                        Toast.makeText(this@DGoals, "Success: " + error?.message, Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        println("Failure: " + error.message)
                        Toast.makeText(this@DGoals, "Failure: " + error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }

        btnMax?.setOnClickListener(){
            val orders = MinMaxGoals(hours, mins)

            //dbReference.push().setValue(orders)
            val pushRef = dbReference.push()
            pushRef.setValue(orders, object :DatabaseReference.CompletionListener
            {
                override fun onComplete(error: DatabaseError?, ref: DatabaseReference)
                {
                    if(error == null)
                    {
                        Toast.makeText(this@DGoals, "Success: " + error?.message, Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        println("Failure: " + error.message)
                        Toast.makeText(this@DGoals, "Failure: " + error.message, Toast.LENGTH_SHORT).show()
                    }
                }
            })

        }

        buttonHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}