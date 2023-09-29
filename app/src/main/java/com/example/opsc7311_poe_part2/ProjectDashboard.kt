package com.example.opsc7311_poe_part2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProjectDashboard : AppCompatActivity()
{
    var btnHome : ImageButton ?= null
    var txtProjectName : TextView ?= null
    private var recv: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_dashboard)

        val bundle: Bundle? = intent.extras
        val projName = bundle!!.getString("project_name")

        btnHome = findViewById(R.id.ib_drawer)

        txtProjectName = findViewById(R.id.Project_Name)

        txtProjectName?.text = projName

        recv?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        btnHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}