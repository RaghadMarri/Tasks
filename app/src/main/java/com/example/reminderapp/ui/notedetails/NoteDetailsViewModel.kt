package com.example.reminderapp.ui.notedetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.database.NoteRepository
import com.example.reminderapp.database.NoteRoomDatabase
import com.example.reminderapp.database.Notes
import kotlinx.coroutines.launch



class NoteDetailsViewModel(application: Application, private val noteIdentifier: String?) : AndroidViewModel(application) {


    companion object {
        private const val TAG = "NotesEditViewModel"
    }
    val noteList:LiveData<List<Notes>>
    private val repository:NoteRepository

    init{
        val noteDao=NoteRoomDatabase.getDatabase(application).noteDao()
        repository=NoteRepository(noteDao)
        noteList=repository.getNoteList()
        initializeNote()

    }

    private lateinit var editableNote: Notes



    val titleEditText = MutableLiveData<String>()

    val descriptionEditText = MutableLiveData<String>()

    val completedCheckBox = MutableLiveData<Boolean>()



    fun initializeNote() {
        viewModelScope.launch {
            if (noteIdentifier != null) {
                editableNote = repository.getNoteById(noteIdentifier) ?: Notes()
            } else {
                editableNote = Notes()
            }
            Log.d(TAG, "identifier is ${editableNote.noteIdentifier}")
            updateUI()
        }
    }

    fun updateUI() {
        titleEditText.value = editableNote.title
        descriptionEditText.value = editableNote.description
        completedCheckBox.value = editableNote.isComplete
    }

    override fun onCleared() {
        super.onCleared()
    }


    fun saveNote() {
        if (titleEditText.value.isNullOrEmpty() || descriptionEditText.value.isNullOrEmpty()) {

        } else {
            updateNote()
            viewModelScope.launch {
                if (noteIdentifier == null) {
                    repository.insertNote(editableNote)
                } else {
                    repository.updateNote(editableNote)
                }

            }
        }
    }

    fun deleteNote(note:Notes){
        viewModelScope.launch {
            repository.delete(note)

        }

    }

    fun updateNote() {
        editableNote.apply {
            title = titleEditText.value!!
            description = descriptionEditText.value!!
            //isComplete = completedCheckBox.value!!

        }

    }





}