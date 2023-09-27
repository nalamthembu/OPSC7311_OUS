package com.example.opsc7311_poe_part2

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView

class HomeScreenActivity : AppCompatActivity()
{
    //Burger Menu Vars
    lateinit var burger_menu: ActionBarDrawerToggle
    var drawButton : ImageButton ?= null
    var closeDrawButton : ImageButton ?= null

    //Create Project Vars
    var add_project: ImageButton ?= null

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
                R.id.item_timeline_view -> Toast.makeText(applicationContext, "Clicked Timeline View", Toast.LENGTH_SHORT).show()
                R.id.item_daily_goals -> Toast.makeText(applicationContext, "Clicked Daily Goals", Toast.LENGTH_SHORT).show()
                R.id.item_sign_out -> startActivity(Intent(this, MainActivity::class.java))
                R.id.item_settings -> Toast.makeText(applicationContext, "Clicked Settings", Toast.LENGTH_SHORT).show()
                R.id.item_help -> Toast.makeText(applicationContext, "Clicked Help", Toast.LENGTH_SHORT).show()
                R.id.item_about_us -> Toast.makeText(applicationContext, "Clicked About Us", Toast.LENGTH_SHORT).show()
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

            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)

            viewPopup.layoutParams = layoutParams

            containerPopup.addView(viewPopup)
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