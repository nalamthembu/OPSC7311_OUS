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
import android.view.View
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
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FetchDataFromDataBaseProjects : AppCompatActivity()
{
    private lateinit var projectRecyclerView: RecyclerView
    private lateinit var projectList: ArrayList<ProjectData>

    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?)
    {
        Toast.makeText(applicationContext, "Tried Loading Projects", Toast.LENGTH_SHORT).show()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        projectRecyclerView = findViewById(R.id.myRecycler)
        projectRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        projectList = arrayListOf<ProjectData>()

        getProjectDataFromFirebase()
    }

    private fun getProjectDataFromFirebase() {
        projectRecyclerView.visibility = View.GONE

        dbReference = FirebaseDatabase.getInstance().getReference("Projects")

        dbReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                projectList.clear()
                if(snapshot.exists()){
                    for(projectSnap in snapshot.children){
                        val projData = projectSnap.getValue(ProjectData::class.java)
                        projectList.add(projData!!)
                    }
                    val mProjectAdapter = UserAdapter(projectList)
                    projectRecyclerView.adapter = mProjectAdapter

                    projectRecyclerView.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}