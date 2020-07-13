package com.example.reminderapp.reciever

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, p1: Intent?) {
        Log.d("onReceive", "p1$p1")
        val i = Intent(context, AlarmService::class.java)
        context?.startService(i)
    }
}