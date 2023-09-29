package com.example.opsc7311_poe_part2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7311_poe_part2.model.ProjectData
import com.example.opsc7311_poe_part2.model.TaskData
import com.example.opsc7311_poe_part2.view.TaskAdapter
import com.example.opsc7311_poe_part2.view.UserAdapter

class ProjectDashboard : AppCompatActivity()
{
    var btnHome : ImageButton ?= null
    var txtProjectName : TextView ?= null
    private var recv:RecyclerView ?=null
    private lateinit var userList: ArrayList<TaskData>
    private lateinit var taskAdapter: TaskAdapter
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

        recv = findViewById(R.id.myRecycler)
        userList = ArrayList()
        taskAdapter = TaskAdapter(this, userList as ArrayList<TaskData>)
        recv?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recv?.adapter = taskAdapter

// adding task to project popup
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

            val taskDescrip = viewTask.findViewById<EditText>(R.id.tDescription)
            val taskCurrDate = viewTask.findViewById<EditText>(R.id.tCurrentDate)
            val taskStartTime = viewTask.findViewById<EditText>(R.id.tStartTime)
            val taskEndTime = viewTask.findViewById<EditText>(R.id.tEndTime)

            createTask.setOnClickListener()
            {
                val tDescrip = taskDescrip.text.toString()
                val tCurrDate = taskCurrDate.text.toString()
                val tStartTime = taskStartTime.text.toString()
                val tEndTime = taskEndTime.text.toString()

                //TO-DO : NEED TO VALIDATE DATE
                if (tDescrip.isNullOrBlank() || tCurrDate.isNullOrBlank() || tStartTime.isNullOrBlank() || tEndTime.isNullOrBlank())
                {
                    Toast.makeText(this, "Make sure both fields are populated", Toast.LENGTH_SHORT)
                        .show();
                    return@setOnClickListener;
                }

                //ASSUMING DATE_ON_POP_UP_MENU_IS_NOT_NULL
                //val date_string = dateOnPopMenu?.text.toString()

                userList.add(TaskData(taskDescrip.text.toString(), taskCurrDate.text.toString(), taskStartTime.text.toString(), taskEndTime.text.toString())) //OLD_CODE -> projDate.text.toString()
                taskAdapter.notifyDataSetChanged()
                taskContainer.removeView(viewTask)
                btnAddTask?.isEnabled = true

                taskContainer.removeView(viewTask)
                btnAddTask?.isEnabled = true
            }

            cancelTask.setOnClickListener()
            {
                taskContainer.removeView(viewTask)
                btnAddTask?.isEnabled = true
            }
        }

        btnHome?.setOnClickListener(){
            onBackPressed()
        }
    }
}