package com.example.reminderapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY _id DESC")
    fun getNoteList(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Notes)

    @Update
    suspend fun updateNote(note: Notes)

    @Query("Delete from note_table")
    suspend fun deleteAllNotes()

    @Query("SELECT * FROM note_table WHERE completed= 1 ORDER BY _id DESC")
    fun getCompletedNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM note_table WHERE note_identifier = :noteId")
    suspend fun getNoteById(noteId: String): Notes?

    @Query("SELECT * FROM note_table WHERE note_identifier = :noteId")
    fun getNoteLiveDataById(noteId: String): LiveData<Notes>

    @Delete
    suspend fun delete(note: Notes)

    @Query("SELECT * FROM note_table ORDER BY _id DESC LIMIT 1")
    suspend fun getLatestNotes(): Notes?

}