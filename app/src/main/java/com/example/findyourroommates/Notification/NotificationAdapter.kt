package com.example.findyourroommates.Notification

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourroommates.R

class NotificationAdapter(private val userlist: ArrayList<notification_data>) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setTitle: TextView = itemView.findViewById(R.id.notification_title2)
        val setMessage: TextView = itemView.findViewById(R.id.notification_message2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewnotification, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userlist[position]
        holder.setTitle.text = currentItem.title
        holder.setMessage.text = currentItem.description
    }
}
