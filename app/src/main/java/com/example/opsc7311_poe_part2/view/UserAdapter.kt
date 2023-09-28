package com.example.opsc7311_poe_part2.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7311_poe_part2.R
import com.example.opsc7311_poe_part2.model.ProjectData

class UserAdapter(val c:Context,val userList: ArrayList<ProjectData>): RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{
    inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v)
    {
        val Name = v.findViewById<TextView>(R.id.project_Name)
        val DueDate = v.findViewById<TextView>(R.id.project_dueDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.project_item,parent,false)
        return UserViewHolder(v)
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int)
    {
        val newList = userList[position]
        holder.Name.text = newList.projectName
        holder.DueDate.text = newList.projectDueDate
    }
    override fun getItemCount(): Int
    {
        return userList.size
    }

}