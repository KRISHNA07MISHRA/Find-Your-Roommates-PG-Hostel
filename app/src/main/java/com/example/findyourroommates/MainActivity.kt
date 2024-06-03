package com.example.findyourroommates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.findyourroommates.fragments.Homescreen
import com.example.findyourroommates.fragments.notificationfragment
import com.example.findyourroommates.fragments.profilefragment
import com.example.findyourroommates.security.Login
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {
   lateinit var button: Button
   lateinit var homefragment:LinearLayout
   lateinit var noticicationfragment:LinearLayout
   lateinit var profilefragment:LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        homefragment = findViewById(R.id.homefragment)
        noticicationfragment = findViewById(R.id.notificationfragment)
        profilefragment = findViewById(R.id.profilefragment)


        replaceFragment(Homescreen(),false)
        homefragment.setOnClickListener {
            replaceFragment(Homescreen(),true)
        }
        noticicationfragment.setOnClickListener {
            replaceFragment(notificationfragment(),true)
        }
        profilefragment.setOnClickListener {
            replaceFragment(profilefragment(),true)
        }
    }

    private fun replaceFragment(fragment: Fragment,addtobackstack:Boolean) {
        val fragmentTransaction:FragmentTransaction = supportFragmentManager.beginTransaction()

        if(addtobackstack == false){
            fragmentTransaction.add(R.id.container,fragment)
        }
        else{
            fragmentTransaction.replace(R.id.container,fragment)
        }
        fragmentTransaction.commit()
    }




}