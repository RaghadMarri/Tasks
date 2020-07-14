package com.example.reminderapp.ui.notedetails

import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider


import com.example.reminderapp.R
import com.example.reminderapp.database.Notes
import com.example.reminderapp.databinding.FragmentNoteDetailsBinding

/**
 * A simple [Fragment] subclass.
 */
class NoteDetailsFragment : Fragment() {

    private lateinit var editNotetitle: EditText
    private lateinit var editNoteDescription: EditText
    private lateinit var note: Notes
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
        val toolbar=binding.myToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)


       editNotetitle.setText(noteTitle)
        editNoteDescription.setText(noteDescription)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_details_fragment_menu,menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                android.R.id.home -> {
                    (activity as AppCompatActivity).onBackPressed()
                    true

                }
                else -> false
            }
        }


}
