package com.example.reminderapp.ui.notedetails

import android.app.*
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.reminderapp.MainActivity
import com.example.reminderapp.R
import com.example.reminderapp.databinding.TimeDatePickerDialogBinding
import com.example.reminderapp.util.sendNotification
import java.util.*


class TimeDatePickerDialog() : DialogFragment(), TimePickerDialog.OnTimeSetListener,
    DatePickerDialog.OnDateSetListener {


    private lateinit var repeat: TextView
    private lateinit var done: TextView
    private lateinit var cancel: TextView
    private lateinit var pickedDate: TextView
    private lateinit var pickedTime: TextView
    private lateinit var binding: TimeDatePickerDialogBinding
    private var repeatValueList = arrayOf("No Repeat", "Daily", "Monthly", "Yearly")
    private val bottomSheetViewModel: BottomSheetViewModel by activityViewModels()


    var day = 0
    var month = 0
    var year = 0
    var time = 0
    var minutes = 0

    var savedDay = ""
    var savedMonth = ""
    var savedYear = ""
    var savedTime = 0
    var savedMinutes = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = TimeDatePickerDialogBinding.inflate(
            inflater,
            container,
            false
        )
        binding.lifecycleOwner = this

        bottomSheetViewModel.datePickerText.observe(this, androidx.lifecycle.Observer {

            pickedDate.text = bottomSheetViewModel.datePickerText.value
        })

        bottomSheetViewModel.timePickerText.observe(this, androidx.lifecycle.Observer {
            pickedTime.text = bottomSheetViewModel.timePickerText.value
        })

        bottomSheetViewModel.noteRepeatUnit.observe(this, androidx.lifecycle.Observer {
            repeat.text = bottomSheetViewModel.noteRepeatUnit.value
        })

        repeat = binding.repeat
        pickedDate = binding.setDate
        pickedTime = binding.setTime
        cancel = binding.cancel
        done = binding.done

        initView()
        createChannel(
            getString(R.string.reminder_notification_channel_id),
            getString(R.string.reminder_notification_channel_name)
        )

        return binding.root
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_LOW
            )

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"

            val notificationManager = requireActivity().getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedTime = hourOfDay
        savedMinutes = minute
        bottomSheetViewModel.timePickerText.value = "$savedTime:$savedMinutes"


    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth.toString()
        var currentMonth = month + 1
        savedMonth = currentMonth.toString()
        savedYear = year.toString()
        bottomSheetViewModel.datePickerText.value = "$savedDay,$savedMonth $savedYear"


    }

    private fun getDateTimeCalendar() {
        val c = Calendar.getInstance()
        day = c.get(Calendar.DAY_OF_MONTH)
        month = c.get(Calendar.MONTH)
        year = c.get(Calendar.YEAR)
        time = c.get(Calendar.HOUR)
        minutes = c.get(Calendar.MINUTE)


    }

    fun onDatePickerClicked() {
        getDateTimeCalendar()
        DatePickerDialog(requireContext(), R.style.PickerDialogTheme, this, year, month, day).show()
    }

    fun onTimePickerClicked() {
        getDateTimeCalendar()
        TimePickerDialog(
            requireContext(),
            R.style.PickerDialogTheme,
            this,
            time,
            minutes,
            true
        ).show()
    }


    private fun initView() {

        pickedDate.setOnClickListener {
            onDatePickerClicked()
        }
        pickedTime.setOnClickListener {
            onTimePickerClicked()
        }

        cancel.setOnClickListener {
            dismiss()
        }


        done.setOnClickListener {
            val notificationManager = ContextCompat.getSystemService(
                requireContext(),
                NotificationManager::class.java
            ) as NotificationManager
            notificationManager.sendNotification(
                requireContext().getString(R.string.message_subscribed),
                requireContext()
            )
            dismiss()
        }

        repeat.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Repeat")
            builder.setIcon(R.drawable.ic_repeat_black_24dp)
            builder.setSingleChoiceItems(repeatValueList, -1) { dialogInterface, i ->
                when (repeatValueList[i]) {
                    "No Repeat" -> {
                        bottomSheetViewModel.noteRepeatValue.value = false
                        bottomSheetViewModel.noteRepeatUnit.value = "never"
                    }
                    "Daily" -> {
                        bottomSheetViewModel.noteRepeatValue.value = true
                        bottomSheetViewModel.noteRepeatUnit.value = "Daily"
                    }
                    "Monthly" -> {
                        bottomSheetViewModel.noteRepeatValue.value = true
                        bottomSheetViewModel.noteRepeatUnit.value = "Monthly"
                    }
                    "Yearly" -> {
                        bottomSheetViewModel.noteRepeatValue.value = true
                        bottomSheetViewModel.noteRepeatUnit.value = "Yearly"
                    }

                }
                repeat.text = this.repeatValueList[i]
                dialogInterface.dismiss()
            }
            builder.setNeutralButton("Cancel") { dialog, which ->
                dialog.cancel()
            }

            val dialog = builder.create()
            dialog.show()

        }


    }


}



