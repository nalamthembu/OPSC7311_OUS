package com.example.opsc7311_poe_part2

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.service.autofill.UserData
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7311_poe_part2.model.ProjectData
import com.example.opsc7311_poe_part2.view.UserAdapter
import com.google.android.material.navigation.NavigationView

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

    //Double Back
    private var doubleBack = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        //Burger Menu Sets
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navigation : NavigationView = findViewById(R.id.navView)
        drawButton = findViewById(R.id.ib_drawer)


        //Create Project Sets
        add_project = findViewById(R.id.Add_Project)
        recv = findViewById(R.id.myRecycler)
        userList = ArrayList()
        userAdapter = UserAdapter(this, userList as ArrayList<ProjectData>)
        recv.layoutManager = LinearLayoutManager(this)
        recv.adapter = userAdapter

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
                R.id.item_sign_out -> startActivity(Intent(this, MainActivity::class.java))
            }
            true
        }

        //Create Project Pop Up
        add_project?.setOnClickListener()
        {
            val popupInflater = LayoutInflater.from(applicationContext)
            val viewPopup = popupInflater.inflate(R.layout.create_project,null)
            val containerPopup = findViewById<RelativeLayout>(R.id.rel_layout)

            val layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )

            val projName = viewPopup.findViewById<EditText>(R.id.pName)
            val projDate = viewPopup.findViewById<EditText>(R.id.pDate)

            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

            viewPopup.layoutParams = layoutParams

            containerPopup.addView(viewPopup)

            val createProj:Button = viewPopup.findViewById(R.id.btn_create_proj)

            createProj.setOnClickListener()
            {
                val pName = projName.text.toString()
                val pDate = projDate.text.toString()
                userList.add(ProjectData(projName.text.toString()+"name: $pName",projDate.text.toString()+"date: $pDate"))
                userAdapter.notifyDataSetChanged()
            }

            val cancelProj:Button = viewPopup.findViewById(R.id.btn_cancel_proj)

            cancelProj.setOnClickListener()
            {

            }
        }
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