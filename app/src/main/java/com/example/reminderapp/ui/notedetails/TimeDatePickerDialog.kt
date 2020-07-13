package com.example.reminderapp.ui.notedetails
import android.app.*
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.reminderapp.R
import com.example.reminderapp.database.Notes
import java.util.Calendar

class TimeDatePickerDialog(context: Context) : Dialog(context),TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener  {

    private lateinit var pickDate: TextView
    private lateinit var pickTime: TextView
    private lateinit var repeat: TextView
    private lateinit var done: TextView
    private lateinit var cancel: TextView
    private lateinit var selectedDate: String
    private lateinit var selectedTime: String


    var day=0
    var month=0
    var year=0
    var time=0
    var minutes=0

    var savedDay=""
    var savedMonth=""
    var savedYear=""
    var savedTime=0
    var savedMinutes=0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.time_date_picker_dialog)

        pickDate=findViewById(R.id.setDate)
        pickTime=findViewById(R.id.setTime)
        repeat=findViewById(R.id.repeat)
        cancel=findViewById(R.id.cancel)
        done=findViewById(R.id.done)

        initView()


    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedTime=hourOfDay
        savedMinutes=minute
        var time="$savedTime:$savedMinutes"
        selectedTime=time
        pickTime.text=selectedTime
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay=dayOfMonth.toString()
        var currentMonth=month+1
        savedMonth=currentMonth.toString()
        savedYear=year.toString()
        var date="$savedDay,$savedMonth $savedYear"
        selectedDate=date

        pickDate.text = selectedDate

    }

    private fun getDateTimeCalendar(){
        val c=Calendar.getInstance()
        day=c.get(Calendar.DAY_OF_MONTH)
        month=c.get(Calendar.MONTH)
        year=c.get(Calendar.YEAR)
        time=c.get(Calendar.HOUR)
        minutes=c.get(Calendar.MINUTE)

    }

    private fun initView() {

        pickDate.setOnClickListener {
            getDateTimeCalendar()
            DatePickerDialog(context, R.style.PickerDialogTheme, this, year, month, day).show()

        }

        pickTime.setOnClickListener {
            getDateTimeCalendar()
            TimePickerDialog(context, R.style.PickerDialogTheme, this, time, minutes, true).show()
        }

        cancel.setOnClickListener {
            dismiss()
        }


       done.setOnClickListener {


            Log.i("BottomSheet", "The note is $selectedTime $selectedDate")
            dismiss()}


    }

    }



