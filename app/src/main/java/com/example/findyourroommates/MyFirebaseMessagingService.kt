package com.example.findyourroommates

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

const val channelId = "notification_channel"
const val channelName = "com.example.findyourroommates"

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private lateinit var database: DatabaseReference
    private lateinit var auth:FirebaseAuth

    override fun onMessageReceived(message: RemoteMessage) {
        database = Firebase.database.reference
        auth = Firebase.auth

        if (message.notification != null) {
            val title = message.notification!!.title ?: "No Title"
            val body = message.notification!!.body ?: "No Body"
            Log.d("FCM", "Notification Title: $title")
            Log.d("FCM", "Notification Body: $body")
            generateNotification(title, body)
            storeToFirebase(title, body)
        } else {
            Log.d("FCM", "Message data payload: ${message.data}")
        }
    }

    private fun storeToFirebase(title: String, body: String) {
        val user = auth.currentUser
        val userid = user?.uid.toString()
        val messageDetail = NotificationDetail(title, body)
        database.child("notification").child(userid).push().setValue(messageDetail)
            .addOnSuccessListener {
                Toast.makeText(this, "Notification stored successfully", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to store notification", Toast.LENGTH_LONG).show()
            }
    }

    private fun getRemoteView(title: String, messaging: String): RemoteViews {
        val remoteView = RemoteViews(packageName, R.layout.notificationlayout)
        remoteView.setTextViewText(R.id.notification_title, title)
        remoteView.setTextViewText(R.id.notification_message, messaging)
        remoteView.setImageViewResource(R.id.notification_logo, R.drawable.notification)
        return remoteView
    }

    private fun generateNotification(title: String, messaging: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.notification)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
            .setContent(getRemoteView(title, messaging))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, builder.build())
    }

    data class NotificationDetail(val title: String? = null, val description: String? = null)
}
