package com.example.opsc7311_poe_part2.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7311_poe_part2.R
import com.example.opsc7311_poe_part2.model.ProjectData


class UserAdapter(val userList: ArrayList<ProjectData>): RecyclerView.Adapter<UserAdapter.UserViewHolder>()
{

    // clickable
    private lateinit var mListener : onClickListener

    interface onClickListener
    {
        fun onItemClick(itemName: String, itemDate : String)
    }

    fun setOnItemClickListener(listener: onClickListener)
    {
        mListener = listener
    }

    // recyclerView
    inner class UserViewHolder(val v:View, listener: onClickListener):RecyclerView.ViewHolder(v)
    {
        val Name = v.findViewById<TextView>(R.id.project_Name)
        val DueDate = v.findViewById<TextView>(R.id.project_dueDate)

        init
        {
            //OLD_CODE = adapterPosition
            v.setOnClickListener{
                listener.onItemClick(Name.text.toString(), DueDate.toString())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.project_item,parent,false)
        return UserViewHolder(v,mListener)
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