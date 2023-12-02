package com.example.opsc7311_poe_part2

import android.annotation.SuppressLint
import android.app.DatePickerDialog
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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
class InsertProjectDataToFireBase : AppCompatActivity()
{
    private lateinit var projectName: EditText
    private lateinit var projectDate: EditText
    private lateinit var btnCreateProjectAndSave: Button

    private lateinit var dbReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.create_project)

        projectName = findViewById(R.id.pName)
        projectDate = findViewById(R.id.btnPickDate)
        btnCreateProjectAndSave = findViewById(R.id.btn_create_proj)

        dbReference = FirebaseDatabase.getInstance().getReference("Projects")
        
    }
}