package com.example.opsc7311_poe_part2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NavUtils
import kotlin.time.*

class ClockingSystem : AppCompatActivity()
{

    //Variables
    private var currentClockStatus: Boolean = false;

    @OptIn(ExperimentalTime::class)
    private var currentTime: TimeSource.Monotonic? = null;
    private var firstMark: Duration? = null;
    private var secondMark: Duration? = null;

    @OptIn(ExperimentalTime::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clocking_system)

        //Initialise CurrentTime
        val cTime = TimeSource.Monotonic;
        currentTime = TimeSource.Monotonic;
    }

    //Called when you need to know the start time (always 0:00:00)
    public fun getFirstMark() : Duration? {
        if (firstMark == null) {
            error("The first mark isn't initialised!");
            return null;
        }

        return firstMark;
    }

    //Called when you need to know the end time (variable)
    public fun getSecondMark() : Duration? {
        if (secondMark == null) {
            error("The second mark isn't initialised!");
            return null;
        }

        return secondMark;
    }

    //Called by the press of the Clock button
    @OptIn(ExperimentalTime::class)
    public fun SetClockStatus(status: Boolean) {
        if (!currentClockStatus && status == true) {

            //TO-DO : Change the text on the Clock Button from whatever it is now to 'Clock Out'

            //Init clock if it isn't already
            if (currentTime == null) {
                currentTime = TimeSource.Monotonic;
            }

            firstMark = currentTime?.markNow()?.elapsedNow();
            //Start up the clock
            currentClockStatus = true;
        }

        if (currentClockStatus && status == false) {

            //TO-DO : Change the text on the Clock Button from whatever it is now to 'Clock In'

            //Mark second point
            secondMark = currentTime?.markNow()?.elapsedNow();
            //Make the clock stop
            currentClockStatus = false;

            //Nullify the clock.
            currentTime = null;
        }
    }
}