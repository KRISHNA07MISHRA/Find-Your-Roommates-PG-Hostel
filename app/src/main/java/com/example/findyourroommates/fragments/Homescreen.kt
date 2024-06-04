package com.example.findyourroommates.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class Homescreen : Fragment(),Adapterclass.OnItemClickListener {

    private lateinit var add: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<hoteldata>
    private lateinit var userAdapter: Adapterclass
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var babu:DatabaseReference

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

        auth = FirebaseAuth.getInstance()


        userArrayList = arrayListOf()
        userAdapter = Adapterclass(userArrayList,this)
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

    override fun viewdetail(nameofhostel: String) {
        val dialogview = LayoutInflater.from(context).inflate(R.layout.hosteldetail,null)
        val hostelname = dialogview.findViewById<TextView>(R.id.sethotelname)
        val hosteladdress = dialogview.findViewById<TextView>(R.id.sethoteladdress)
        val hostelprice = dialogview.findViewById<TextView>(R.id.sethotelprice)
        val hostelphone = dialogview.findViewById<TextView>(R.id.sethotelphone)
        val hostelpic = dialogview.findViewById<ImageView>(R.id.sethotelpic)
        val hostelmetro = dialogview.findViewById<TextView>(R.id.sethotelnearmetro)
        val hostelmarket = dialogview.findViewById<TextView>(R.id.sethotelmarket)
        val hostelfood = dialogview.findViewById<TextView>(R.id.sethotelfood)

        auth.currentUser?.let {
            babu = FirebaseDatabase.getInstance().getReference("hoteldetail").child(nameofhostel)
            babu.get().addOnSuccessListener {
                val name = it.child("name").getValue(String::class.java)
                hosteladdress.text = it.child("address").getValue(String::class.java)
                hostelprice.text = it.child("price").getValue(String::class.java)
                hostelphone.text = it.child("phone").getValue(String::class.java)
                val hotelpic = it.child("simage").getValue(String::class.java)
                hostelmetro.text = it.child("metro").getValue(String::class.java)
                hostelmarket.text = it.child("market").getValue(String::class.java)
                hostelfood.text= it.child("food").getValue(String::class.java)

                hostelname.setText(name)






                Picasso.get().load(hotelpic).into(hostelpic)

                val dialog = AlertDialog.Builder(requireContext())
                    .setView(dialogview)
                    .setPositiveButton("OK",null)
                    .create()
                dialog.show()
            }
        }
    }

}
