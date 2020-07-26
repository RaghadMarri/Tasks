package com.example.reminderapp.ui.notelist

import android.app.Application
import androidx.lifecycle.*
import com.example.reminderapp.database.NoteRepository
import com.example.reminderapp.database.source.local.NoteRoomDatabase
import com.example.reminderapp.database.Notes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class NoteListViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "NoteListViewModel"
    }

    val noteList: LiveData<List<Notes>>
    private val repository: NoteRepository

    init {
        val noteDao =
            NoteRoomDatabase.getDatabase(application).noteDao()
        repository =
            NoteRepository(noteDao)
        noteList = repository.getNoteList()

    }

    private val noteListViewModelJob = Job()

    override fun onCleared() {
        noteListViewModelJob.cancel()
    }

    val empty: LiveData<Boolean> = Transformations.map(noteList) {
        it.isEmpty()
    }

    fun isEmpty(): Boolean? {
        return noteList.value?.isEmpty()
    }

    val scope = CoroutineScope(Dispatchers.Main + noteListViewModelJob)

    val notes = repository.getNoteList()

    fun deleteAllNote() = scope.launch {
        repository.deleteAllNotes()
    }

    fun completeTask(note: Notes, completed: Boolean) = viewModelScope.launch {
        if (completed) {

            note.isComplete = true
            repository.updateNote(note)


        } else {
            note.isComplete = false
            repository.updateNote(note)


        }
    }

    fun deleteAllCompletedNotes() {
        viewModelScope.launch {
            repository.deleteAllCompletedNotes()
        }
    }


}