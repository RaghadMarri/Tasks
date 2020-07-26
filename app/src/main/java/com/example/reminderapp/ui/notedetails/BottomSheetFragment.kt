package com.example.reminderapp.ui.notedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.reminderapp.R
import com.example.reminderapp.databinding.AddingNoteBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var editNewNote: EditText
    private lateinit var editNoteDetails: EditText
    private lateinit var editSaveNewNote: TextView
    private lateinit var editAddNoteDetails: ImageView
    private lateinit var editAddDate: ImageView
    lateinit var pickedDate: String

    private lateinit var binding: AddingNoteBottomSheetBinding

    private val bottomSheetViewModel: BottomSheetViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle)
        bottomSheetViewModel.noteIdentifier = -1


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = AddingNoteBottomSheetBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = bottomSheetViewModel

        initView()


    }

    private fun initView() {


        editSaveNewNote = binding.save
        editNewNote = binding.noteTitle
        editNoteDetails = binding.addingDetailsEditeText
        editAddNoteDetails = binding.noteDetailsImageView
        editAddDate = binding.datePickerImageView

        editAddNoteDetails.setOnClickListener {
            editNoteDetails.visibility = View.VISIBLE
        }


        editAddDate.setOnClickListener {
            val timeDatePickerDialog = TimeDatePickerDialog()
            timeDatePickerDialog.show(childFragmentManager, timeDatePickerDialog.tag)


        }


        editSaveNewNote.setOnClickListener {
            bottomSheetViewModel.saveNote()

            NavHostFragment.findNavController(this).navigate(R.id.noteListFragment)
            dismiss()
        }


    }

}


