package com.example.reminderapp.ui.notedetails

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.reminderapp.Event
import com.example.reminderapp.database.NoteRepository
import com.example.reminderapp.database.source.local.NoteRoomDatabase
import com.example.reminderapp.database.Notes
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class BottomSheetViewModel(application: Application) : AndroidViewModel(application) {


    companion object {
        private const val TAG = "BottomSheetViewModel"
    }

    var noteIdentifier by Delegates.notNull<Int>()

    private val repository: NoteRepository
    private var editableNote: Notes = Notes()
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    fun initializeNote() {
        viewModelScope.launch {

            when {
                noteIdentifier == -1 -> {
                    editableNote = Notes()
                }
                noteIdentifier != null -> {
                    editableNote = repository.getNoteById(noteIdentifier) ?: Notes()

                    Log.i(
                        "Details",
                        "the is is $noteIdentifier The init is ${editableNote.title} ,  ${editableNote.description}"
                    )
                }
                else -> {
                    editableNote = Notes()
                }
            }
            updateUI()

        }
    }


    private fun updateUI() {
        titleEditText.value = editableNote.title
        detailsEditText.value = editableNote.description
        datePickerText.value = editableNote.date
        timePickerText.value = editableNote.time
        noteRepeatUnit.value = editableNote.repeatUnit


    }


    init {
        val noteDao = NoteRoomDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)

    }


    val titleEditText = MutableLiveData<String>()
    val detailsEditText = MutableLiveData<String>()
    val noteRepeatValue = MutableLiveData<Boolean>()
    val noteRepeatUnit = MutableLiveData<String>()


    val datePickerText = MutableLiveData<String>()

    val timePickerText = MutableLiveData<String>()


    override fun onCleared() {
        super.onCleared()
    }

    fun saveNote() {

        if (titleEditText.value.isNullOrEmpty() || detailsEditText.value.isNullOrEmpty()) {

        } else {
            updateNote()
            viewModelScope.launch {
                repository.insertNote(editableNote)

                Log.i(
                    "BottomSheet",
                    "RThe note is ${editableNote.title} and the details is ${editableNote.description} and the data and time are " +
                            "${editableNote.date} , ${editableNote.time} and can I repeat ${editableNote.repeat} and the repeat unit is ${editableNote.repeatUnit}"
                )


            }
            Log.i("BottomSheet", "The note is ${editableNote.date}")
        }
    }

    fun updateNote() {
        editableNote.apply {
            title = titleEditText.value!!
            description = detailsEditText.value!!
            date = datePickerText?.value ?: ""
            time = timePickerText?.value ?: ""
            repeat = noteRepeatValue?.value ?: false
            repeatUnit = noteRepeatUnit?.value ?: "never"
        }
    }

    fun deleteNote(id: Int) {
        viewModelScope.launch {
            repository.delete(id)

        }

    }


    fun updateExistingNote(): View.OnClickListener {
        if (titleEditText.value.isNullOrEmpty() || detailsEditText.value.isNullOrEmpty()) {

        } else {
            updateNote()
            viewModelScope.launch {
                repository.updateNote(editableNote)
            }
            Log.i(
                "BottomSheet",
                "RThe note is ${editableNote.title} and the details is ${editableNote.description} and the data and time are " +
                        "${editableNote.date} , ${editableNote.time} and can I repeat ${editableNote.repeat} and the repeat unit is ${editableNote.repeatUnit}"
            )


        }

        return View.OnClickListener {
            val direction =
                NoteDetailsFragmentDirections.actionNoteDetailsFragmentToNoteListFragment()
            it.findNavController().navigate(direction)
        }
    }


}