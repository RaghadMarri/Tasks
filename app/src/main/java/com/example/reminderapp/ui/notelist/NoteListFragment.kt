package com.example.reminderapp.ui.notelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminderapp.R
import com.example.reminderapp.database.Notes
import com.example.reminderapp.databinding.FragmentNoteListBinding
import com.example.reminderapp.ui.notedetails.BottomSheetFragment



class NoteListFragment() : Fragment(),NoteListAdapter.NoteClickListener {


    companion object {
        private const val TAG = "NoteListFragment"
    }

    private lateinit var noteAdapter: NoteListAdapter
    private lateinit var noteViewModel: NoteListViewModel
    private lateinit var binding: FragmentNoteListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        context?: return binding.root
        noteAdapter = NoteListAdapter(this)


        binding.recyclerview.apply {
            adapter = noteAdapter
            layoutManager=LinearLayoutManager(context)

        }

        val fab = binding.addNoteFab
        fab.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)



        }

        subscrubeUi(binding,noteAdapter)


        return binding.root
    }


    private fun subscrubeUi(binding: FragmentNoteListBinding,adapter: NoteListAdapter){

        noteViewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
        noteViewModel.noteList.observe(viewLifecycleOwner, Observer { notes->
            notes?.let { noteAdapter.setItem(it) }


        })

    }

    override fun onNoteClickListener(data: Notes) {

        val bundle = bundleOf(
            "NoteTitle" to data.title,
            "NoteDetails" to data.description,
            "NoteDate" to data.date,
            "NoteTime" to data.time,
            "NoteRepeat" to data.repeat
        )

        findNavController().navigate(
            R.id.action_noteListFragment_to_noteDetailsFragment,
            bundle
        )


    }



}
