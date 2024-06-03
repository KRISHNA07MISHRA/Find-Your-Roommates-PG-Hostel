package com.example.findyourroommates

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso

class Profile : AppCompatActivity() {
    private lateinit var profilename:EditText
    private lateinit var profileemail:EditText
    private lateinit var profilephone:EditText
    private lateinit var profileaddress:EditText
    private lateinit var profiledone:Button
    private lateinit var img:ImageView
    private lateinit var sImage :String

    private lateinit var database:DatabaseReference
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        profilename = findViewById(R.id.profilename)
        profileemail = findViewById(R.id.profileemail)
        profilephone = findViewById(R.id.profilephone)
        profileaddress = findViewById(R.id.profileaddress)
        profiledone = findViewById(R.id.profiledone)
        img = findViewById(R.id.img)

        database = Firebase.database.reference
        auth = Firebase.auth

        img.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            imagelauncher.launch(intent)
        }

        profiledone.setOnClickListener {
            val name = profilename.text.toString()
            val email = profileemail.text.toString()
            val phone = profilephone.text.toString()
            val address = profileaddress.text.toString()

            if(name.isEmpty()||email.isEmpty()||phone.isEmpty()||address.isEmpty()){
                Toast.makeText(this,"please fill all your info",Toast.LENGTH_LONG).show()

            }

            storetofirestore(name,email,phone,address)
        }
    }

    private fun storetofirestore(name: String, email: String, phone: String, address: String) {
        val User = auth.currentUser
        if(User != null){
            val userId = User.uid
            val userInfo = userinfo(name,email,phone,address,sImage)

            database.child("users").child(userId).setValue(userInfo)
                .addOnSuccessListener {
                    Toast.makeText(this,"Save",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
        }
    }


    val imagelauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            if(it.data != null){
                val ref = Firebase.storage.reference.child("profile")
                ref.putFile(it.data!!.data!!).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        Picasso.get().load(it.toString()).into(img)
                        sImage = it.toString()
                    }
                }
            }
        }
    }
    data class userinfo(val name:String,val email:String,val phone:String,val address:String,val sImage:String)
}