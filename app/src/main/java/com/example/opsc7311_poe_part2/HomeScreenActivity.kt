package com.example.opsc7311_poe_part2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class HomeScreenActivity : AppCompatActivity()
{
    //Burger Menu Vars
    lateinit var burger_menu: ActionBarDrawerToggle

    //Create Project Vars
    var add_project: ImageButton ?= null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        //Burger Menu Sets
        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navigation : NavigationView = findViewById(R.id.navView)

        //Create Project Sets
        add_project = findViewById(R.id.Add_Project)

        //Burger Menu Action Bar
        burger_menu = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(burger_menu)
        burger_menu.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigation.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_inbox -> Toast.makeText(applicationContext, "Clicked Inbox",Toast.LENGTH_SHORT).show()
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
}