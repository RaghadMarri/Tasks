package com.example.reminderapp.ui.notedetails

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.Event
import com.example.reminderapp.R
import com.example.reminderapp.database.NoteRepository
import com.example.reminderapp.database.NoteRoomDatabase
import com.example.reminderapp.database.Notes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BottomSheetViewModel(application: Application) : AndroidViewModel(application) {


    companion object {
        private const val TAG = "BottomSheetViewModel"
    }

    val noteList: LiveData<List<Notes>>
    private val repository: NoteRepository
    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    init{
        val noteDao= NoteRoomDatabase.getDatabase(application).noteDao()
        repository= NoteRepository(noteDao)
        noteList=repository.getNoteList()

    }

    private var taskId: String? = null
    private var isNewTask: Boolean = false
    val titleEditText = MutableLiveData<String>()
    val detailsEditText = MutableLiveData<String>()
    private val datePickerText = MutableLiveData<String>()
    val dateText: LiveData<String>
        get() = datePickerText

    private val timePickerText = MutableLiveData<String>()
    val timeText: LiveData<String>
        get() =timePickerText

    override fun onCleared() {
        super.onCleared()
    }

    fun saveTask(note:Notes) {
        val currentTitle = titleEditText.value
        val currentDescription = detailsEditText.value

        if (currentTitle == null) {
            _snackbarText.value = Event(R.string.empty_task_message)
            return
        }

        val currentTaskId = taskId
        if (isNewTask || currentTaskId == null) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.insertNote(note)
            }
        } else {
            viewModelScope.launch(Dispatchers.IO) {
                repository.updateNote(note)
            }
        }
    }










}