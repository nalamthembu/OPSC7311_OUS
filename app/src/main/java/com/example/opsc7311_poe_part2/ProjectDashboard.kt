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
import android.os.SystemClock
import android.util.Log
import android.view.Choreographer
import android.widget.ImageView
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ProjectDashboard : AppCompatActivity() {
    var btnHome: ImageButton? = null
    var txtProjectName: TextView? = null
    private var recv: RecyclerView? = null
    private lateinit var userList: ArrayList<TaskData>
    private lateinit var taskAdapter: TaskAdapter
    var btnAddTask: ImageButton? = null

    //Punch in System Global Vars
    var btnOpenClockingSystem: ImageButton? = null;
    var txtVTimeSpent: TextView? = null;

    private var running = false
    private var startTime: Long = 0
    var elapsedTime : Long = 0;
    var lastRecordedTime : String = "";
    //End of Punch In Global Vars


    private var dateOnPopMenu: TextView? = null
    private var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_dashboard)

        //DATABASE
        val database = Firebase.database
        val dbReference = database.getReference("Clock in Times")

        //START OF PUNCH IN CODE

        // Start the frame callback
        Choreographer.getInstance().postFrameCallback(frameCallback)

        btnOpenClockingSystem = findViewById(R.id.btnPunchInClock)

        btnOpenClockingSystem?.setOnClickListener()
        {
            val taskInflater = LayoutInflater.from(applicationContext)
            val viewTask = taskInflater.inflate(R.layout.activity_clocking_system, null)
            val taskContainer = findViewById<RelativeLayout>(R.id.rel_layout)

            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )

            params.addRule(RelativeLayout.CENTER_IN_PARENT)
            viewTask.layoutParams = params
            taskContainer.addView(viewTask)

            var btnClockIn: Button = viewTask.findViewById(R.id.btnClockInOut)
            txtVTimeSpent = viewTask.findViewById(R.id.txtTimeSpent)

            btnClockIn.setOnClickListener()
            {
                if (running) {
                    // Stop the stopwatch
                    running = false
                    btnClockIn.text = "Clock In"
                    txtVTimeSpent?.text = lastRecordedTime

                    //Save to database.
                    val customRecordId = "ClockInID" // Replace with the desired custom ID

// Save to database with a specific ID.
                    val pushRef = dbReference.child(customRecordId)
                    pushRef.setValue(lastRecordedTime, object : DatabaseReference.CompletionListener {
                        override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                            if (error == null) {
                                Log.d("MyTag", "Data Inserted successfully")
                                Toast.makeText(this@ProjectDashboard, "Success: " + error?.message, Toast.LENGTH_SHORT).show()
                            } else {
                                println("Failure: " + error.message)
                                Toast.makeText(this@ProjectDashboard, "Failure: " + error.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    })

                } else {
                    // Start the stopwatch
                    running = true
                    btnClockIn.text = "Clock Out"
                    startTime = SystemClock.uptimeMillis()
                    txtVTimeSpent?.text = "0:00:000" // Reset to 0:00:000


                }
            }

        }

        //END OF PUNCH IN CODE

        val bundle: Bundle? = intent.extras
        val projName = bundle!!.getString("project_name")

        btnHome = findViewById(R.id.ib_drawer)
        btnAddTask = findViewById(R.id.Add_Tasks)

        txtProjectName = findViewById(R.id.Project_Name)

        txtProjectName?.text = projName

        recv = findViewById(R.id.myRecycler)
        userList = ArrayList()
        taskAdapter = TaskAdapter(this, userList as ArrayList<TaskData>)
        recv?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recv?.adapter = taskAdapter

        Load()

// adding task to project popup
        btnAddTask?.setOnClickListener() {
            btnAddTask?.isEnabled = false
            val taskInflater = LayoutInflater.from(applicationContext)
            val viewTask = taskInflater.inflate(R.layout.create_task, null)
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
                if (tDescrip.isNullOrBlank() || tCurrDate.isNullOrBlank() || tStartTime.isNullOrBlank() || tEndTime.isNullOrBlank()) {
                    Toast.makeText(this, "Make sure both fields are populated", Toast.LENGTH_SHORT)
                        .show();
                    return@setOnClickListener;
                }

                //Image Picker
                imageView = findViewById(R.id.imageView)
                val button: Button = viewTask.findViewById(R.id.floatingActionButton)

                button?.setOnClickListener() {
                    Toast.makeText(applicationContext, "Button Clicked", Toast.LENGTH_SHORT).show()
                    ImagePicker.with(this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(
                            1080,
                            1080
                        )    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start()
                }
                //End Image Picker

                userList.add(
                    TaskData(
                        taskDescrip.text.toString(),
                        tCurrDate,
                        taskStartTime.text.toString(),
                        taskEndTime.text.toString()
                    )
                )

                taskAdapter.notifyDataSetChanged()

                taskContainer.removeView(viewTask)
                btnAddTask?.isEnabled = true


                //SAVE_INFORMATION
                Save(tDescrip, tStartTime, tEndTime, tCurrDate);
            }

            cancelTask.setOnClickListener()
            {
                taskContainer.removeView(viewTask)
                btnAddTask?.isEnabled = true
            }
        }

        btnHome?.setOnClickListener() {
            onBackPressed()
        }
    }

    private fun Save(desc: String, startTime: String, endTime: String, currDate: String) {
        //AFTER A TASK IS CREATED - SAVE IT TO THE DISK
        var context = applicationContext;
        val path = context.filesDir.path

        //CREATE_DIR
        val letDirectory = File(path, "LET")
        letDirectory.mkdirs()

        //CREATE_FILE
        val file = File(letDirectory, "Tasks.txt")

        //PARSE_RECORD_INTO_A_READABLE_FORMAT
        var record: String = "";

        val SEPERATOR = "#";

        record += desc + SEPERATOR

        record += startTime + SEPERATOR

        record += endTime + SEPERATOR

        record += currDate + SEPERATOR

        record += "*" //end_of_file

        if (file != null) {
            FileOutputStream(file).use {
                it.write(record.toByteArray())

                Toast.makeText(this, "Saved Task to internal storage", Toast.LENGTH_SHORT)
                    .show();
            }
        } else {
            file.appendText(record)
        }
    }

    private fun Load() {
        var context = applicationContext
        val path = context.filesDir.path

        //ADDED FROM CREATE TASK CODE
        val taskInflater = LayoutInflater.from(applicationContext)
        val viewTask = taskInflater.inflate(R.layout.create_task, null)
        val taskContainer = findViewById<RelativeLayout>(R.id.rel_layout)

        //GET_DIR
        val letDirectory = File(path, "LET")

        //FILE_IN_QUESTION
        val file = File(letDirectory, "Tasks.txt")

        if (file.exists()) {
            try {
                val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }

                var taskDescrip: String = ""
                var taskCurrentDate: String = ""
                var taskStartTime: String = ""
                var taskEndTime: String = ""

                var index: Int = 0

                var SEPARATOR: Char = '#'
                var END_OF_FILE: Char = '*'

                //TASK_DESCRIPTION
                while (index < inputAsString.length && inputAsString[index] != SEPARATOR) {
                    taskDescrip += inputAsString[index]
                    index++
                }

                //CURRENT_DATE
                index++
                while (index < inputAsString.length && inputAsString[index] != SEPARATOR) {
                    taskCurrentDate += inputAsString[index]
                    index++
                }

                //START_TIME
                index++
                while (index < inputAsString.length && inputAsString[index] != SEPARATOR) {
                    taskStartTime += inputAsString[index]
                    index++
                }

                //END_TIME
                index++
                while (index < inputAsString.length && inputAsString[index] != SEPARATOR && inputAsString[index] != END_OF_FILE) {
                    taskEndTime += inputAsString[index]
                    index++
                }

                userList.clear()

                //SET THESE
                userList.add(TaskData(taskDescrip, taskCurrentDate, taskStartTime, taskEndTime))

                runOnUiThread {
                    taskAdapter.notifyDataSetChanged()
                    btnAddTask?.isEnabled = true
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error reading file", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            runOnUiThread {
                Toast.makeText(this, "File does not exist", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clickDatePicker() {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                Toast.makeText(
                    this, "Year was $selectedYear, month was ${selectedMonth + 1}, " +
                            "day was $selectedDayOfMonth", Toast.LENGTH_LONG
                ).show()

                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"

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

    private val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            if (running) {
                elapsedTime = SystemClock.uptimeMillis() - startTime
                val seconds = (elapsedTime / 1000).toInt()
                val minutes = seconds / 60
                val hours = minutes / 60

                val timeString = String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60)

                // Update the last recorded time only if running is true
                lastRecordedTime = timeString

                // Update the displayed time
                if (txtVTimeSpent != null)
                    txtVTimeSpent?.text = timeString
            }

            // Continue the frame callback
            Choreographer.getInstance().postFrameCallback(this)
        }
    }

    override fun onResume() {
        super.onResume()
        // Start the frame callback when the activity is resumed
        Choreographer.getInstance().postFrameCallback(frameCallback)
    }

    override fun onPause() {
        super.onPause()
        // Stop the frame callback when the activity is paused
        Choreographer.getInstance().removeFrameCallback(frameCallback)
    }
}