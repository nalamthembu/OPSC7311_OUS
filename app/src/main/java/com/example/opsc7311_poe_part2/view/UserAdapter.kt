package com.example.opsc7311_poe_part2.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7311_poe_part2.R
class UserAdapter()
{
    inner class userViewHolder(val v:View):RecyclerView.ViewHolder(v)
    {
        val project_Name = v.findViewById<TextView>(R.id.project_Name)
    }
}