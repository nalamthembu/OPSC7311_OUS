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
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class TimelineView : AppCompatActivity() {

    var buttonHome : ImageButton?= null

    lateinit var data: ArrayList<Entry>
    lateinit var linedata: LineDataSet
    lateinit var lineset:LineData
    lateinit var linechart:LineChart
    var convert : Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_view)
        buttonHome = findViewById(R.id.ib_tv_home)
        linechart = findViewById(R.id.line_chart)

        val database = Firebase.database
        val readMin = database.getReference("DailyGoalMin")
        val readMax = database.getReference("DailyGoalMax")


        data = ArrayList<Entry>()

        readMin.addValueEventListener(object: ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                for(childSnapshot in snapshot.children)
                {
                    val h = childSnapshot.child("hours").value
                    val m = childSnapshot.child("mins").value
                    val hoursval = h.toString()
                    val minsval = m.toString()
                    convert = (hoursval.toFloat() * 100) + (minsval.toFloat())
                    println(convert)
                    Log.d("my_data", "data $convert")
                    data.add(Entry(0f,convert))
                    data.add(Entry(100f,convert))
                }
                linedata = LineDataSet(data,"Entries")
                linechart.description.text = "Hours worked"
                lineset = LineData(linedata)

                linechart.data = lineset
                linedata.setColors(ColorTemplate.MATERIAL_COLORS,255)
                linedata.valueTextColor = Color.WHITE
                linechart.notifyDataSetChanged()
                linechart.invalidate()
            }

            override fun onCancelled(error: DatabaseError) {
                println("Firebase Error: " + error.message)
            }
        }
        )

        val imageView = findViewById<ImageView>(R.id.ivImage)
        Glide.with(this).load(R.drawable.tokoloshietime).into(imageView)

        buttonHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}