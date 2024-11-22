package com.example.radio.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class MyApp : Application() {
    lateinit var notificationManager  : NotificationManager


    companion object{
        val CHANNEL_ID="Foreground Service"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    "Radio Notification"
                    , NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }

}