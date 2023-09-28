package com.example.opsc7311_poe_part2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class DGoals : AppCompatActivity() {

    var buttonHome : ImageButton ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dgoals)

        buttonHome = findViewById(R.id.ib_dg_home)

        buttonHome?.setOnClickListener(){
            startActivity(Intent(this, HomeScreenActivity::class.java))
        }
    }
}