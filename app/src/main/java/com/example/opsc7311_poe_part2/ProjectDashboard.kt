package com.example.opsc7311_poe_part2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProjectDashboard : AppCompatActivity()
{
    var btnHome : ImageButton ?= null
    var txtProjectName : TextView ?= null
    private var recv: RecyclerView? = null
    var btnAddTask : ImageButton ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_dashboard)

        val bundle: Bundle? = intent.extras
        val projName = bundle!!.getString("project_name")

        btnHome = findViewById(R.id.ib_drawer)
        btnAddTask = findViewById(R.id.Add_Tasks)

        txtProjectName = findViewById(R.id.Project_Name)

        txtProjectName?.text = projName

        recv?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        btnAddTask?.setOnClickListener(){
            btnAddTask?.isEnabled = false
            val taskInflater = LayoutInflater.from(applicationContext)
            val viewTask = taskInflater.inflate(R.layout.create_task,null)
            val taskContainer = findViewById<RelativeLayout>(R.id.rel_layout)

            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )

            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            viewTask.layoutParams = params
            taskContainer.addView(viewTask)

            val createTask: Button = viewTask.findViewById(R.id.btn_create_proj)
            val cancelTask: Button = viewTask.findViewById(R.id.btn_cancel_proj)

            createTask.setOnClickListener(){


                taskContainer.removeView(viewTask)
                btnAddTask?.isEnabled = true
            }

            cancelTask.setOnClickListener(){
                taskContainer.removeView(viewTask)
                btnAddTask?.isEnabled = true
            }
        }

        btnHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}