package com.example.findyourroommates.recyclerviewfile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourroommates.R
import com.squareup.picasso.Picasso

class Adapterclass(private val userList: ArrayList<hoteldata>) : RecyclerView.Adapter<Adapterclass.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setname: TextView = itemView.findViewById(R.id.hotelname1)
        val setaddress: TextView = itemView.findViewById(R.id.hoteladdress1)
        val setprice: TextView = itemView.findViewById(R.id.hotelprice1)
        val setphoto: ImageView = itemView.findViewById(R.id.hotelphoto1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.viewdata, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.setname.text = currentItem.name
        holder.setaddress.text = currentItem.address
        holder.setprice.text = currentItem.price
        if (!currentItem.simage.isNullOrBlank()) {
            Picasso.get()
                .load(currentItem.simage)
                .into(holder.setphoto)
        } else {
            // If hotelpic is empty or null, you can load a placeholder image or leave it blank
            // For example:
            // Picasso.get().load(R.drawable.placeholder_image).into(holder.setphoto)
            // or
            // holder.setphoto.setImageDrawable(null)
        }
    }
}

