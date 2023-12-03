package com.example.opsc7311_poe_part2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class TimelineView : AppCompatActivity() {

    var buttonHome : ImageButton?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_view)
        buttonHome = findViewById(R.id.ib_tv_home)

        buttonHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}