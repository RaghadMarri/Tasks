package com.example.reminderapp.ui.notedetails

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.reminderapp.R
import com.example.reminderapp.database.Notes
import com.example.reminderapp.databinding.AddingNoteBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*



class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var editNewNote: EditText
    private lateinit var editNoteDetails: EditText
    private lateinit var editSaveNewNote: TextView
    private lateinit var editAddNoteDetails: ImageView
    private lateinit var editAddDate: ImageView
    private lateinit var binding: AddingNoteBottomSheetBinding

   private val bottomSheetViewModel: BottomSheetViewModel by  activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding=AddingNoteBottomSheetBinding.inflate(inflater, container, false)

        binding.lifecycleOwner=this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // bottomSheetViewModel = ViewModelProvider(this).get(BottomSheetViewModel::class.java)
        binding.viewModel= bottomSheetViewModel

        initView()


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    private fun initView() {

        editSaveNewNote= binding.save
        editNewNote=binding.noteTitle
        editNoteDetails=binding.addingDetailsEditeText
        editAddNoteDetails=binding.noteDetailsImageView
        editAddDate=binding.datePickerImageView

        editAddNoteDetails.setOnClickListener {
            editNoteDetails.visibility=View.VISIBLE
           }


         editAddDate.setOnClickListener{
             val timeDatePickerDialog = TimeDatePickerDialog(requireContext())
             timeDatePickerDialog.show()

         }


        editSaveNewNote.setOnClickListener {
            var title = editNewNote.text.toString()
            var details=editNoteDetails.text.toString()
            var note:Notes= Notes(title=title,description = details)
            bottomSheetViewModel.saveTask(note)
            Log.i("BottomSheet", "The note is $note")
            dismiss()}


    }


}


