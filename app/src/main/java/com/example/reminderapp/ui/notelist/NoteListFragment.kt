package com.example.reminderapp.ui.notelist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reminderapp.R
import com.example.reminderapp.databinding.FragmentNoteListBinding
import com.example.reminderapp.ui.notedetails.BottomSheetFragment
import com.example.reminderapp.ui.notedetails.BottomSheetViewModel


class NoteListFragment() : Fragment() {

    companion object {
        private const val TAG = "NoteListFragment"
    }

    private lateinit var noteAdapter: NoteListAdapter
    private lateinit var noteViewModel: NoteListViewModel
    private lateinit var binding: FragmentNoteListBinding
    private val bottomSheetViewModel: BottomSheetViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentNoteListBinding.inflate(inflater, container, false)
        context ?: return binding.root
        noteViewModel = ViewModelProvider(this).get(NoteListViewModel::class.java)
        noteAdapter = NoteListAdapter(noteViewModel)


        binding.recyclerview.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(context)

        }

        val fab = binding.addNoteFab
        fab.setOnClickListener {
            bottomSheetViewModel.noteIdentifier = -1
            bottomSheetViewModel.initializeNote()
            val bottomSheetFragment = BottomSheetFragment()
            bottomSheetFragment.show(childFragmentManager, bottomSheetFragment.tag)

        }

        subscrubeUi(binding, noteAdapter)
        setHasOptionsMenu(true)

        return binding.root
    }


    private fun subscrubeUi(binding: FragmentNoteListBinding, adapter: NoteListAdapter) {
        noteViewModel.noteList.observe(viewLifecycleOwner, Observer { notes ->
            notes?.let { noteAdapter.setItem(it) }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.deletCompleted -> {

                noteViewModel.deleteAllCompletedNotes()
                true
            }
            else -> false
        }

    }


}
