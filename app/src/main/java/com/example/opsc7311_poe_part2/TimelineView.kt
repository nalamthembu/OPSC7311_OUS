package com.example.opsc7311_poe_part2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.Description
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class TimelineView : AppCompatActivity() {

    var buttonHome : ImageButton?= null

    lateinit var data: ArrayList<Entry>
    lateinit var linedata: LineDataSet
    lateinit var lineset:LineData
    lateinit var linechart:LineChart

    lateinit var entries1List: ArrayList<Entry>
    lateinit var linedata1set:LineDataSet

    lateinit var entries2List: ArrayList<Entry>
    lateinit var linedata2set: LineDataSet

    var convert : Float = 0f
    var conv2:Float=0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_view)

        // Initialize other views and variables...
        linechart = findViewById(R.id.line_chart)

        val database = Firebase.database
        val readMin = database.getReference("DailyGoalMin")
        val readMax = database.getReference("DailyGoalMax")

        val data = ArrayList<Entry>()

        // Fetch data from the first source
        fetchData(readMin, data)

        // Fetch data from the second source
        fetchData(readMax, data)
    }

    private fun fetchData(reference: DatabaseReference, data: ArrayList<Entry>) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data
                data.clear()

                for (childSnapshot in snapshot.children) {
                    val h = childSnapshot.child("hours").value
                    val m = childSnapshot.child("mins").value
                    val hoursval = h.toString()
                    val minsval = m.toString()
                    val convert = (hoursval.toFloat() * 100)
                    println(convert)
                    Log.d("my_data", "data $convert")

                    // Add data to the list inside the callback
                    data.add(Entry(25f, convert))
                }

                if(data.isNotEmpty())
                    updateChart()
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase Error: " + error.message)
            }
        })
    }

    private fun updateChart() {
        // Create LineDataSet and LineData inside the callback
        val linedata = LineDataSet(data, "Entries")
        linechart.description.text = "Hours worked"
        val lineset = LineData(linedata, linedata1set, linedata2set)

        // Set colors for the line
        linedata.setColors(ColorTemplate.MATERIAL_COLORS, 255)
        linedata.valueTextColor = Color.WHITE

        // Set the LineData to the chart
        linechart.data = lineset

        // Notify the chart that the data has changed
        linechart.notifyDataSetChanged()
        linechart.invalidate()
    }
}

