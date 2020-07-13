package com.example.reminderapp.reciever

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.reminderapp.R

class AlarmService : IntentService("ToDoListAppAlarmReceiver") {
    private var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun onHandleIntent(intent: Intent?) {

        if(null == intent){
            Log.d("AlarmService", "onHandleIntent( OH How? )")
        }
    }

    private fun showNotification(taskDescription: String) {
        Log.d("AlarmService", "showNotification($taskDescription)")
        val CHANNEL_ID = "taoist_alarm_channel_01"
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Time Up!")
            .setContentText(taskDescription)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(23, mBuilder.build())
    }
}