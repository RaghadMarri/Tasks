package com.example.reminderapp.ui.notedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.example.reminderapp.R
import com.example.reminderapp.databinding.FragmentNoteDetailsBinding

/**
 * A simple [Fragment] subclass.
 */
class NoteDetailsFragment : Fragment() {

    private lateinit var editNotetitle: EditText
    private lateinit var editNoteDescription: EditText
    private lateinit var binding: FragmentNoteDetailsBinding

    var noteTitle: String? = null
    private var noteDescription: String? = null
    private var noteDate: String? = null
    private var noteTime: String? = null
    private var noteRepeat = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        noteTitle= arguments?.getString("NoteTitle")
        noteDescription= arguments?.getString("NoteDetails")
        noteDate= arguments?.getString("NoteDate")
        noteTime= arguments?.getString("NoteTime")
     //   noteRepeat = arguments?.getString("NoteRepeat")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)
        editNotetitle=binding.noteTitle
        editNoteDescription=binding.noteDetails

       editNotetitle.setText(noteTitle)
        editNoteDescription.setText(noteDescription)
        return binding.root
    }

}
