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
    var convert : Float = 0f
    var l1:Float = 0f
    var l2:Float = 100f
    var count:Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_view)
        buttonHome = findViewById(R.id.ib_tv_home)
        linechart = findViewById(R.id.line_chart)

        val database = Firebase.database
        val readMin = database.getReference("DailyGoalMin")
        val readMax = database.getReference("DailyGoalMax")
        val readtimes = database.getReference("Clock In Times")


        data = ArrayList<Entry>()
        entries1List = ArrayList<Entry>()

        fetchData(readMin,data)
        fetchData(readMax,entries1List)


        val imageView = findViewById<ImageView>(R.id.ivImage)
        Glide.with(this).load(R.drawable.tokoloshietime).into(imageView)

        buttonHome?.setOnClickListener(){
            onBackPressed()
        }
    }

    private fun fetchData(reference: DatabaseReference, data: ArrayList<Entry>) {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Clear existing data for this source
                data.clear()

                for (childSnapshot in snapshot.children) {
                    val h = childSnapshot.child("hours").value
                    val m = childSnapshot.child("mins").value
                    val hours = h.toString()
                    convert = hours.toFloat() * 100
                        // Add data to the list inside the callback
                    println(count)

                    data.add(Entry(l1,convert))
                    data.add(Entry(l2,convert))
                }

                // Create LineDataSet and LineData inside the callback
                linedata = LineDataSet(data, reference.toString())

                // Set the LineData to the chart
                lineset = LineData(linedata)
                linechart.data = lineset

                // Set colors for the line
                linedata.setColors(Color.BLUE)
                linedata!!.valueTextColor = Color.WHITE

                // Notify the chart that the data has changed
                linechart.notifyDataSetChanged()
                linechart.invalidate()
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase Error: " + error.message)
            }
        })
    }
}