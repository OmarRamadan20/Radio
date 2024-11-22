package com.example.radio

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.radio.app.MyApp.Companion.CHANNEL_ID

class MyService : Service() {


    private fun displayNotification() {

        val notificationRemoteIntent = RemoteViews(this.packageName, R.layout.notification_layout)



        var notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.radio_ic)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationRemoteIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()
        startForeground(100,notification)
    }

    override fun onBind(intent: Intent): IBinder ?=null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        displayNotification()
        return START_STICKY
    }


}