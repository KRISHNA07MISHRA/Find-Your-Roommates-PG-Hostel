package com.example.findyourroommates.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourroommates.R
import com.example.findyourroommates.recyclerviewfile.AddDetailofHostel
import com.example.findyourroommates.recyclerviewfile.Adapterclass
import com.example.findyourroommates.recyclerviewfile.hoteldata
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Homescreen : Fragment() {

    private lateinit var add: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<hoteldata>
    private lateinit var userAdapter: Adapterclass
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_homescreen, container, false)




        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        add = view.findViewById(R.id.add)
        recyclerView = view.findViewById(R.id.recyclerview)
        add.setOnClickListener {
            startActivity(Intent(context, AddDetailofHostel::class.java))

        }

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        database = FirebaseDatabase.getInstance().getReference("hoteldetail")

        userArrayList = arrayListOf()
        userAdapter = Adapterclass(userArrayList)
        recyclerView.adapter = userAdapter

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userArrayList.clear() // Clear the list before adding new data
                for (dataSnapshot in snapshot.children) {
                    val user = dataSnapshot.getValue(hoteldata::class.java)
                    userArrayList.add(user!!)
                }
                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Homescreen", "Database error: ${error.message}")
            }

        })

    }

}
