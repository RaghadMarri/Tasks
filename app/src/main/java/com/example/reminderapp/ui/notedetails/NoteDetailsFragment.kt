package com.example.reminderapp.ui.notedetails

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.example.reminderapp.R
import com.example.reminderapp.databinding.FragmentNoteDetailsBinding


class NoteDetailsFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailsBinding
    private val args: NoteDetailsFragmentArgs by navArgs()
    private val bottomSheetViewModel: BottomSheetViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bottomSheetViewModel.noteIdentifier = args.noteId
        bottomSheetViewModel.initializeNote()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNoteDetailsBinding.inflate(inflater, container, false)



        binding.lifecycleOwner = this
        binding.viewModel = bottomSheetViewModel

        binding.pickDateTime.setOnClickListener {
            val timeDatePickerDialog = TimeDatePickerDialog()
            timeDatePickerDialog.show(childFragmentManager, timeDatePickerDialog.tag)

        }

        bottomSheetViewModel.datePickerText.observe(viewLifecycleOwner, Observer {
            binding.dateDetails.text = bottomSheetViewModel.datePickerText.value
        })

        val toolbar = binding.myToolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.task_details_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_note -> {
                bottomSheetViewModel.deleteNote(NoteDetailsFragmentArgs.fromBundle(requireArguments()).noteId)
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_noteDetailsFragment_to_noteListFragment)
                true
            }

            android.R.id.home -> {
                bottomSheetViewModel.updateExistingNote()
                (activity as AppCompatActivity).onBackPressed()
                true

            }
            else -> false
        }

    }

}
