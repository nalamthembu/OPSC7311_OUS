package com.example.opsc7311_poe_part2

import android.app.DatePickerDialog
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
import com.example.opsc7311_poe_part2.model.TaskData
import com.example.opsc7311_poe_part2.view.TaskAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ProjectDashboard : AppCompatActivity()
{
    var btnHome : ImageButton ?= null
    var txtProjectName : TextView ?= null
    private var recv:RecyclerView ?=null
    private lateinit var userList: ArrayList<TaskData>
    private lateinit var taskAdapter: TaskAdapter
    var btnAddTask : ImageButton ?= null
    private var dateOnPopMenu : TextView ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_dashboard)

        val bundle: Bundle? = intent.extras
        val projName = bundle!!.getString("project_name")
        //val projDate = bundle!!.getString("project_date")

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
            val btnCurrentTaskDate = viewTask.findViewById<Button>(R.id.tCurrentDate)
            val taskStartTime = viewTask.findViewById<EditText>(R.id.tStartTime)
            val taskEndTime = viewTask.findViewById<EditText>(R.id.tEndTime)


            //DATE_CODE_FOR_EACH_TASK
            //NEW_DATE_CODE
            //Get year, month and day from calendar
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)

            //Open date picker
            btnCurrentTaskDate.setOnClickListener()
            {
                clickDatePicker()
            }
            //END_OF_NEW_DATE_CODE

            createTask.setOnClickListener()
            {
                val tDescrip = taskDescrip.text.toString()
                val tStartTime = taskStartTime.text.toString()
                val tEndTime = taskEndTime.text.toString()

                //ASSUMING DATE_ON_POP_UP_MENU_IS_NOT_NULL
                val tCurrDate = dateOnPopMenu?.text.toString()

                //TO-DO : NEED TO VALIDATE DATE
                if (tDescrip.isNullOrBlank() || tCurrDate.isNullOrBlank() || tStartTime.isNullOrBlank() || tEndTime.isNullOrBlank())
                {
                    Toast.makeText(this, "Make sure both fields are populated", Toast.LENGTH_SHORT)
                        .show();
                    return@setOnClickListener;
                }

                //ASSUMING DATE_ON_POP_UP_MENU_IS_NOT_NULL
                //val date_string = dateOnPopMenu?.text.toString()

                userList.add(TaskData(taskDescrip.text.toString(), tCurrDate/*taskCurrDate.text.toString()*/, taskStartTime.text.toString(), taskEndTime.text.toString())) //OLD_CODE -> projDate.text.toString()
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

    private fun clickDatePicker()
    {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            {_, selectedYear, selectedMonth, selectedDayOfMonth ->

                Toast.makeText(this, "Year was $selectedYear, month was ${selectedMonth+1}, " +
                        "day was $selectedDayOfMonth", Toast.LENGTH_LONG).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth+1}/$selectedYear"

                dateOnPopMenu = findViewById<TextView>(R.id.txtDateOnPopUp)

                dateOnPopMenu?.text = selectedDate;

                //Convert above date(String) to a date object that we can perform calculations on
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

                val theDate = sdf.parse(selectedDate)

            },
            year,
            month,
            day
        )

        dpd.show()
    }
}