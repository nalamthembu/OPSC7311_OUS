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
        data.add(Entry(10f,100f))
        data.add(Entry(20f,500f))
        data.add(Entry(30f,300f))
        data.add(Entry(40f,150f))
        data.add(Entry(50f,750f))

        linedata = LineDataSet(data,"Entries")
        lineset = LineData(linedata)

        linechart.data = lineset
        linedata.setColors(ColorTemplate.MATERIAL_COLORS,255)
        linedata!!.valueTextColor = Color.WHITE

        buttonHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}