package com.example.reminderapp.ui.notelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.database.NoteRepository
import com.example.reminderapp.database.NoteRoomDatabase
import com.example.reminderapp.database.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteListViewModel(application:Application) : AndroidViewModel(application) {

    companion object{
        private const val TAG="NoteListViewModel"
    }

    val noteList:LiveData<List<Notes>>
    private val repository:NoteRepository

    init{
        val noteDao=NoteRoomDatabase.getDatabase(application).noteDao()
        repository=NoteRepository(noteDao)
        noteList=repository.getNoteList()

    }

    val notes = repository.getNoteList()
    fun insert(note:Notes) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertNote(note)
    }

    fun deleteAllNote() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllNotes()
    }

    var isEmpty=true


    fun updateNote(note:Notes) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }




}