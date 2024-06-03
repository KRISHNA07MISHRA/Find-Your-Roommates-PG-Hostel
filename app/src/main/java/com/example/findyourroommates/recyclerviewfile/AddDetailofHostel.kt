package com.example.findyourroommates.recyclerviewfile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.findyourroommates.MainActivity
import com.example.findyourroommates.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso

class AddDetailofHostel : AppCompatActivity() {
    lateinit var hotelimage:ImageView
    lateinit var hotelname:EditText
    lateinit var hoteladdress:EditText
    lateinit var hotelphone:EditText
    lateinit var hotelprice:EditText
    lateinit var metro:EditText
    lateinit var food:EditText
    lateinit var market:EditText
    lateinit var done:Button
    lateinit var sImage:String

    private lateinit var auth:FirebaseAuth
    private lateinit var database:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_detailof_hostel)

        hotelimage = findViewById(R.id.hotelpic)
        hotelname = findViewById(R.id.nameofhostel)
        hoteladdress = findViewById(R.id.hotelofaddress)
        hotelphone = findViewById(R.id.hotelphone)
        hotelprice = findViewById(R.id.hotelprice)
        metro = findViewById(R.id.metro)
        food = findViewById(R.id.food)
        market = findViewById(R.id.market)
        done = findViewById(R.id.hoteldetaildone)

        database = Firebase.database.reference
        auth = Firebase.auth

        hotelimage.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_PICK
            intent.type = "image/*"
            imagelauncher.launch(intent)
        }

        done.setOnClickListener {
            val name = hotelname.text.toString()
            val address = hoteladdress.text.toString()
            val phone = hotelphone.text.toString()
            val price = hotelprice.text.toString()
            val metro = metro.text.toString()
            val food = food.text.toString()
            val market = market.text.toString()

//            if(sImage == ""){
//                Toast.makeText(this,"please select image first",Toast.LENGTH_LONG).show()
//            }

             if(name.isEmpty()||address.isEmpty()||phone.isEmpty()||price.isEmpty()||metro.isEmpty()||food.isEmpty()||market.isEmpty()){
                Toast.makeText(this,"Please fill",Toast.LENGTH_LONG).show()
            }

            writeuserdata(name,address,phone,price,metro,food,market)
        }




    }
    val imagelauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        val curruser = auth.currentUser.toString()
        if(it.resultCode == Activity.RESULT_OK){
            if(it.data != null){
                val ref = Firebase.storage.reference.child("hotelpic").child(curruser)
                ref.putFile(it.data!!.data!!).addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener {
                        Picasso.get().load(it.toString()).into(hotelimage)
                        sImage = it.toString()
                    }
                }
            }
        }
    }

    private fun writeuserdata(name: String, address: String, phone: String, price: String, metro: String, food: String, market: String) {
        val curruser = auth.currentUser
        if(curruser != null){
            val userid = curruser.uid
            val hotelinfo = Hotelinfo(name,address,phone,price,metro,food,market,sImage)

            database.child("hoteldetail").child(name).setValue(hotelinfo).addOnSuccessListener {
                Toast.makeText(this,"success",Toast.LENGTH_LONG).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }
    data class Hotelinfo(val name: String? = null,val address: String? = null,val phone: String? = null,val price: String?=null,val metro: String?=null,val food: String?=null,val market:String? = null,val sImage:String?=null)
}