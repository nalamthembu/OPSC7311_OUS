package com.example.opsc7311_poe_part2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity()
{
    var btnLogin: Button?= null;
    var username: TextView? = null;
    var password: TextView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Text View Code (Username, Password)
        username = findViewById(R.id.txtUsername);
        password = findViewById(R.id.txtPassword);

        //Login Button Code
        btnLogin = findViewById(R.id.btn_login)

        btnLogin?.setOnClickListener()
        {
            startActivity(Intent(this, HomeScreenActivity::class.java))
        }
    }
}