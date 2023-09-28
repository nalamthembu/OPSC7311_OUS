package com.example.opsc7311_poe_part2.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7311_poe_part2.R
import com.example.opsc7311_poe_part2.model.TaskData

class TaskAdapter(val c: Context, val taskList: ArrayList<TaskData>): RecyclerView.Adapter<TaskAdapter.userViewHolder>()
{
    inner class userViewHolder(val v:View):RecyclerView.ViewHolder(v)
    {
        val tName = v.findViewById<TextView>(R.id.tName)
        val currentDate = v.findViewById<TextView>(R.id.tCurrentDate)
        val startTime = v.findViewById<TextView>(R.id.tStartTime)
        val endTime = v.findViewById<TextView>(R.id.tEndTime)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.userViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.task_item,parent,false)
        return userViewHolder(v)
    }

    override fun onBindViewHolder(holder: TaskAdapter.userViewHolder, position: Int)
    {
        val newList = taskList[position]
        holder.tName.text = newList.taskName
        holder.currentDate.text = newList.taskCurrentDate
        holder.startTime.text = newList.taskStartTime
        holder.endTime.text = newList.taskEndTime
    }

    override fun getItemCount(): Int
    {
        return taskList.size
    }

}
