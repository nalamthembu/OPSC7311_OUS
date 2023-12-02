package com.example.opsc7311_poe_part2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ClockingSystem : AppCompatActivity() {

    var currentClockStatus : Boolean = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clocking_system)
    }

    fun SetClockStatus(status : Boolean) {
        if (currentClockStatus && status == false) {
            //Make the clock stop
            currentClockStatus = false;
        }

        if (!currentClockStatus  && status == true)
        {
            //Start up the clock
            currentClockStatus = true;
        }
    }
}