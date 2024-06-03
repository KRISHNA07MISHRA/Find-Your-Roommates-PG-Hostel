package com.example.findyourroommates.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.findyourroommates.R
import com.example.findyourroommates.security.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class profilefragment : Fragment() {
    private lateinit var database:DatabaseReference
    //private lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profilefragment, container, false)

        val img = view.findViewById<ImageView>(R.id.img2)

        val name = view.findViewById<TextView>(R.id.profilename2)
        val email = view.findViewById<TextView>(R.id.profileemail2)
        val phone = view.findViewById<TextView>(R.id.profilephone2)
        val address = view.findViewById<TextView>(R.id.profileaddress2)
        val logout = view.findViewById<Button>(R.id.logout)

        logout.setOnClickListener {
            com.google.firebase.Firebase.auth.signOut()
            startActivity(Intent(activity, Login::class.java))
            requireActivity().finish()
        }

//        auth = Firebase.auth
//        var curruser = auth.currentUser.toString()
        val auth = FirebaseAuth.getInstance().currentUser?.uid

        if(auth != null){
            database = FirebaseDatabase.getInstance().getReference("users").child(auth)
            database.get().addOnSuccessListener {
                val username = it.child("name").getValue(String::class.java)
                val useremail = it.child("email").getValue(String::class.java)
                val userphone = it.child("phone").getValue(String::class.java)
                val useraddress = it.child("address").getValue(String::class.java)
                val userimage = it.child("simage").getValue(String::class.java)

                name.setText(username)
                email.setText(useremail)
                phone.setText(userphone)
                address.setText(useraddress)
                Picasso.get().load(userimage).into(img)
            }



        }
//        if(name == null || email == null || phone == null || address == null || img == null){
//            val name = name.text.toString()
//            val email = email.text.toString()
//            val phone = phone.text.toString()
//            val address = address.text.toString()
//
//            savetodatabase(name,email,phone,address)
//        }

        return view
    }

//    private fun savetodatabase(name: String, email: String, phone: String, address: String) {
//        val currentuser = auth.currentUser
//        if(currentuser != null){
//            val userid = currentuser.uid
//            val detail = Userdat(name,email,phone,address)
//            database.child("users").child(userid).setValue(detail)
//                .addOnSuccessListener {
//                     val  a = currentuser.toString()
//                    database = FirebaseDatabase.getInstance().getReference("users").child(a)
//                    database.get().addOnSuccessListener {
//                        val username = it.child("name").getValue(String::class.java)
//                        val useremail = it.child("email").getValue(String::class.java)
//                        val userphone = it.child("phone").getValue(String::class.java)
//                        val useraddress = it.child("address").getValue(String::class.java)
//
//
//                        name.setText(username)
//                        email.setText(useremail)
//                        phone.setText(userphone)
//                        address.setText(useraddress)
//
//                        }
//                    }
//                }
//
//
//        }
//    }
//    data class Userdat(val name: String?=null,val email: String?= null,val phone: String?=null,val address: String)
//

}