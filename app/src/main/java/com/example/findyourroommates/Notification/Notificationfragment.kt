package com.example.findyourroommates.Notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findyourroommates.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class notificationfragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private val notificationList = ArrayList<notification_data>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notificationfragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerview2)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        notificationAdapter = NotificationAdapter(notificationList)
        recyclerView.adapter = notificationAdapter

        database = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        fetchDataFromFirebase()
    }

    private fun fetchDataFromFirebase() {
        val currentUser = auth.currentUser
        val userId = currentUser?.uid

        if (userId != null) {
            val userNotificationRef = database.child("notification").child(userId)
            userNotificationRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    notificationList.clear()
                    for (dataSnapshot in snapshot.children) {
                        val notification = dataSnapshot.getValue(notification_data::class.java)
                        notification?.let {
                            notificationList.add(it)
                        }
                    }
                    notificationAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle database error
                }
            })
        }
    }
}
