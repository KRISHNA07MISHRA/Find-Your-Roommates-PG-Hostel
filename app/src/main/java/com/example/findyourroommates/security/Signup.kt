package com.example.findyourroommates.security

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.findyourroommates.Profile
import com.example.findyourroommates.R
import com.google.firebase.auth.FirebaseAuth

class Signup : AppCompatActivity() {
    private lateinit var signupname:EditText
    private lateinit var signupemail:EditText
    private lateinit var signuppassword:EditText
    private lateinit var signupconformpassword:EditText
    private lateinit var signup:Button
    private lateinit var signuplogin:Button

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupemail = findViewById(R.id.signupEmail)
        signupname = findViewById(R.id.signupname)
        signuppassword = findViewById(R.id.signuppassword)
        signupconformpassword = findViewById(R.id.signupconformpassword)
        signup = findViewById(R.id.signup)
        signuplogin = findViewById(R.id.signuplogin)

        auth = FirebaseAuth.getInstance()

        signuplogin.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
            finish()
        }

        signup.setOnClickListener {
            val name = signupname.text.toString()
            val email = signupemail.text.toString()
            val password = signuppassword.text.toString()
            val verify = signupconformpassword.text.toString()

            if(email.isEmpty()||name.isEmpty()||password.isEmpty()||verify.isEmpty()){
                Toast.makeText(this,"Please fill all field",Toast.LENGTH_LONG).show()

            }
            else if(verify != password){
                Toast.makeText(this,"Please correct your password",Toast.LENGTH_LONG).show()

            }
            else{
                auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){
                        if(it.isSuccessful){
                            Toast.makeText(baseContext,"Successfully signup",Toast.LENGTH_LONG).show()

                            startActivity(Intent(this,Profile::class.java))
                            finish()
                        }
                        else{
                            Toast.makeText(this,"Authentication failed",Toast.LENGTH_LONG).show()

                        }
                    }
            }
        }
    }
}