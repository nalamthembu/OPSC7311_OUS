package com.example.opsc7311_poe_part2

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.service.autofill.UserData
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opsc7311_poe_part2.model.ProjectData
import com.example.opsc7311_poe_part2.view.TaskAdapter
import com.example.opsc7311_poe_part2.view.UserAdapter
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeScreenActivity : AppCompatActivity()
{
    //Burger Menu Vars
    lateinit var burger_menu: ActionBarDrawerToggle
    var drawButton : ImageButton ?= null
    var closeDrawButton : ImageButton ?= null

    //Create Project Vars
    var add_project: ImageButton ?= null
    private lateinit var recv:RecyclerView
    private lateinit var userList: ArrayList<ProjectData>
    private lateinit var userAdapter:UserAdapter
    private var dateOnPopMenu : TextView ?= null

    //feed
    var feedButton : ImageButton ?= null

    //Double Back
    private var doubleBack = false

    //Camera Variables
    private  var imageView: ImageView?=null



    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        //Burger Menu Sets
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navigation : NavigationView = findViewById(R.id.navView)
        drawButton = findViewById(R.id.ib_drawer)


        val database = Firebase.database
        val dbReference = database.getReference("Projects")

        //GiffManager Start
        val imageView = findViewById<ImageView>(R.id.ivImage)
        Glide.with(this).load(R.drawable.tokoloshie).into(imageView)
        //GiffManager End

        //Create Project Sets
        add_project = findViewById(R.id.Add_Project)
        recv = findViewById(R.id.myRecycler)
        userList = ArrayList()
        userAdapter = UserAdapter(userList as ArrayList<ProjectData>)
        recv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        recv.adapter = userAdapter

        feedButton = findViewById(R.id.FeedTokoloshe)


        userAdapter.setOnItemClickListener(object :UserAdapter.onClickListener { override fun onItemClick(itemName: String, itemDate :String)
            {
                //Toast.makeText(this@HomeScreenActivity, "You Clicked project NO: $position", Toast.LENGTH_SHORT).show()

                var intent : Intent = Intent(this@HomeScreenActivity,ProjectDashboard::class.java)

                //TO-DO : PUT_PROJECT_NAME & DATE _IN_EXTRA

                intent.putExtra("project_name", itemName)
                intent.putExtra("project_date", itemDate)

                startActivity(intent)
            }

        })


        //Burger Menu Action Bar
        burger_menu = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(burger_menu)

        drawButton?.setOnClickListener(){
            closeDrawButton = findViewById(R.id.ib_drawer_close)
            drawerLayout.openDrawer(GravityCompat.START)

            closeDrawButton?.setOnClickListener(){
                drawerLayout.closeDrawer(Gravity.LEFT)
            }
        }



        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigation.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_inbox -> Toast.makeText(applicationContext, "Clicked Inbox",Toast.LENGTH_SHORT).show()
                R.id.item_notifs -> Toast.makeText(applicationContext, "Clicked Notifications", Toast.LENGTH_SHORT).show()
                R.id.item_timeline_view -> startActivity(Intent(this,TimelineView::class.java))
                R.id.item_daily_goals -> startActivity(Intent(this,DGoals::class.java))
                R.id.item_sign_out -> startActivity(Intent(this, MainActivity::class.java))
                R.id.item_settings -> Toast.makeText(applicationContext, "Clicked Settings", Toast.LENGTH_SHORT).show()
                R.id.item_help -> Toast.makeText(applicationContext, "Clicked Help", Toast.LENGTH_SHORT).show()
                R.id.item_about_us -> Toast.makeText(applicationContext, "Clicked About Us", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawer(Gravity.LEFT)
            true
        }

        // feed tokoloshe
        feedButton?.setOnClickListener()
        {
            val imageView = findViewById<ImageView>(R.id.ivImage)
            Glide.with(this).load(R.drawable.tokoloshieapple).into(imageView)

            val handler = Handler()

            // Wait for 2 seconds and then execute the code inside postDelayed
            handler.postDelayed({

                val imageView = findViewById<ImageView>(R.id.ivImage)
                Glide.with(this).load(R.drawable.tokoloshie).into(imageView)

                println("Delayed code executed after 2 seconds")
            }, 2000) // 2000 milliseconds = 2 seconds
        }
        //Create Project Pop Up
        add_project?.setOnClickListener()
        {
            add_project?.isEnabled = false
            val popupInflater = LayoutInflater.from(applicationContext)
            val viewPopup = popupInflater.inflate(R.layout.create_project,null)
            val containerPopup = findViewById<RelativeLayout>(R.id.rel_layout)

            val layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )

            val projName = viewPopup.findViewById<EditText>(R.id.pName)
            val projDate = viewPopup.findViewById<Button>(R.id.btnPickDate) //-> old var name = pDate



            //NEW_DATE_CODE
            //Get year, month and day from calendar
            val c = Calendar.getInstance()
            val day = c.get(Calendar.DAY_OF_MONTH)
            val month = c.get(Calendar.MONTH)
            val year = c.get(Calendar.YEAR)

            //Open date picker
            projDate.setOnClickListener()
            {
                clickDatePicker()
            }
            //END_OF_NEW_DATE_CODE

            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

            viewPopup.layoutParams = layoutParams

            containerPopup.addView(viewPopup)

            val createProj:Button = viewPopup.findViewById(R.id.btn_create_proj)

            /*Image Picker
            imageView = findViewById(R.id.imageView3)
            val button : Button = viewPopup.findViewById(R.id.floatingActionButton)

           /* button?.setOnClickListener(){
                Toast.makeText(applicationContext, "Button Clicked", Toast.LENGTH_SHORT).show()
                ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start()
            }*/
            End Image Picker*/

            createProj.setOnClickListener()
            {

                val pName = projName.text.toString()
                val pDate = dateOnPopMenu?.text.toString()

                //TO-DO : NEED TO VALIDATE DATE
                if (pName.isNullOrBlank()) {
                    Toast.makeText(this, "Make sure both fields are populated", Toast.LENGTH_SHORT)
                        .show();
                    return@setOnClickListener;
                }

                // creating unique key for db
                val projID = dbReference.push().key!!

                val project = ProjectData(projID,pName,pDate)

                //dbReference.child(projID).push().setValue(project)
                // checking if it went through to firebase
                val pushRef = dbReference.push()
                pushRef.setValue(project, object: DatabaseReference.CompletionListener
                {
                    override fun onComplete(error: DatabaseError?, ref: DatabaseReference)
                    {
                        if(error == null)
                        {
                            Log.d("MyTag","Data Inserted successfully")
                            Toast.makeText(this@HomeScreenActivity, "Success: " + error?.message, Toast.LENGTH_SHORT).show()
                        }
                        else
                        {
                            println("Failure: " + error.message)
                            Toast.makeText(this@HomeScreenActivity, "Failure: " + error.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
                
                    //yes

                //ASSUMING DATE_ON_POP_UP_MENU_IS_NOT_NULL
                val date_string = dateOnPopMenu?.text.toString()

                userList.add(ProjectData(projID,projName.text.toString(), date_string)) //OLD_CODE -> projDate.text.toString()
                userAdapter.notifyDataSetChanged()
                containerPopup.removeView(viewPopup)
                add_project?.isEnabled = true
            }

            val cancelProj:Button = viewPopup.findViewById(R.id.btn_cancel_proj)

            cancelProj.setOnClickListener()
            {

                add_project?.isEnabled = true
                containerPopup.removeView(viewPopup)
            }
        }

        // adds projects from firebase if any was created
        //val fetchProjects = Intent(this,FetchDataFromDataBaseProjects::class.java)
        //startActivity(fetchProjects)

    }

    private fun GoToProjectDashboard()
    {

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

        //Dont allow a future date to be selected
        //dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageView?.setImageURI(data?.data)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(burger_menu.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    //Back button double press to exit
    override fun onBackPressed() {
        if(doubleBack){
            super.onBackPressed()
            return
        }
        this.doubleBack = true
        Toast.makeText(this, "Press back again to Exit", Toast.LENGTH_SHORT).show()

        Handler(Looper.getMainLooper()).postDelayed(Runnable { doubleBack = false }, 2000)
    }
}