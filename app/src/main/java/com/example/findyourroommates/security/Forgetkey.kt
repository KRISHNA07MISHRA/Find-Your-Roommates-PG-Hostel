package com.example.findyourroommates.security

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.findyourroommates.R
import com.google.firebase.auth.FirebaseAuth

class Forgetkey : AppCompatActivity() {
    lateinit var forgetemail:EditText
    lateinit var forgetpassword:Button
    lateinit var forgetlogin:Button
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgetkey)

        forgetemail = findViewById(R.id.forgetemail)
        forgetpassword = findViewById(R.id.forgetpassword)
        forgetlogin = findViewById(R.id.forgetlogin)
        auth = FirebaseAuth.getInstance()

        forgetlogin.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
            finish()
        }
        forgetpassword.setOnClickListener {
            val email = forgetemail.text.toString()
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(this,"check email",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"check it again",Toast.LENGTH_LONG).show()
                    }
                }
        }

    }
}