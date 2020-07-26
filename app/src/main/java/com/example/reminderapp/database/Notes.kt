package com.example.reminderapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "note_table")
data class Notes(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "_id") val id:Int=0,
                 @ColumnInfo(name="title")var title:String="",
                 @ColumnInfo(name="description") var  description:String="",
                 @ColumnInfo(name="date")var date:String= SimpleDateFormat("EEE, d MMM yyyy").format(Date()),
                 var time: String = SimpleDateFormat("h:mm a").format(Date()),
                 var repeat:Boolean = false,
                 @ColumnInfo(name = "repeat_unit")
                 var repeatUnit:String="Day",
                 @ColumnInfo(name = "active")
                 var isActive:Boolean = true,
                 @ColumnInfo(name = "completed")
                 var isComplete:Boolean = false) {
}


