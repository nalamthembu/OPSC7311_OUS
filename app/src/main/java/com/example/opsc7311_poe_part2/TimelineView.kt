package com.example.opsc7311_poe_part2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class TimelineView : AppCompatActivity() {

    var buttonHome : ImageButton?= null

    lateinit var data: ArrayList<Entry>
    lateinit var linedata: LineDataSet
    lateinit var lineset:LineData

    lateinit var linechart:LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timeline_view)
        buttonHome = findViewById(R.id.ib_tv_home)
        linechart = findViewById(R.id.line_chart)

        data = ArrayList()
        data.add(Entry(50f,100f))
        data.add(Entry(100f,100f))
        data.add(Entry(150f,500f))
        data.add(Entry(200f,500f))
        data.add(Entry(250f,300f))
        data.add(Entry(300f,300f))
        data.add(Entry(350f,150f))
        data.add(Entry(400f,150f))
        data.add(Entry(450f,750f))
        data.add(Entry(1000f,750f))

        linedata = LineDataSet(data,"Entries")
        lineset = LineData(linedata)

        linechart.data = lineset
        linedata.setColors(ColorTemplate.MATERIAL_COLORS,255)
        linedata.valueTextColor = Color.WHITE

        buttonHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}