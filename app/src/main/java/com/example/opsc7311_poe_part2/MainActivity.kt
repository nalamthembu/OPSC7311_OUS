package com.example.opsc7311_poe_part2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var btnLogin: Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Text View Code (Username, Password)
        val username = findViewById<EditText>(R.id.txtUsername);
        val password = findViewById<EditText>(R.id.txtPassword);

        //Login Button Code
        btnLogin = findViewById(R.id.btn_login)

        btnLogin?.setOnClickListener()
        {

            if (username.text.isNullOrBlank() || password.text.isNullOrBlank()) {
                Toast.makeText(this, "Make sure both fields are populated", Toast.LENGTH_SHORT)
                    .show();
                return@setOnClickListener;
            }

            startActivity(Intent(this, HomeScreenActivity::class.java))
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        }
    }
}