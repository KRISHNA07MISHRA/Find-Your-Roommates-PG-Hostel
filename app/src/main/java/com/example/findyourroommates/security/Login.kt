package com.example.findyourroommates.security

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.findyourroommates.MainActivity
import com.example.findyourroommates.R
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var loginemail:EditText
    private lateinit var loginpassword:EditText
    private lateinit var loginforget:TextView
    private lateinit var login:Button
    private lateinit var loginsignup:Button

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginemail = findViewById(R.id.loginEmail)
        loginpassword = findViewById(R.id.loginpassword)
        loginforget = findViewById(R.id.loginforget)
        login = findViewById(R.id.Login)
        loginsignup = findViewById(R.id.loginsignup)

        auth = FirebaseAuth.getInstance()

        loginsignup.setOnClickListener {
            startActivity(Intent(this,Signup::class.java))
            finish()
        }
        loginforget.setOnClickListener {
            startActivity(Intent(this,Forgetkey::class.java))
            finish()
        }

        val currentUser = auth.currentUser
        if(currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        login.setOnClickListener {
            createlogin()
        }
    }

    private fun createlogin() {
        val email = loginemail.text.toString()
        val password = loginpassword.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please fill all field",Toast.LENGTH_LONG).show()
            return;
        }
        else{
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    if(it.isSuccessful){
                        Toast.makeText(this,"Login successful",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this,"Login failed",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
}