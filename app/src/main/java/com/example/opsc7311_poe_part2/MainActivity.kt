package com.example.opsc7311_poe_part2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity()
{
    var btnLogin: Button?= null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogin = findViewById(R.id.btnLogIn)

        btnLogin?.setOnClickListener()
        {
            startActivity(Intent(this, HomeScreenActivity::class.java))
        }
    }
}