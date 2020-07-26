package com.example.reminderapp.database.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.reminderapp.database.Notes

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

    @Query("SELECT * FROM note_table WHERE _id= :noteId")
    suspend fun getNoteById(noteId: Int): Notes?

    @Query("SELECT * FROM note_table WHERE _id= :noteId")
    fun getNoteLiveDataById(noteId: Int): LiveData<Notes>

    @Query("DELETE FROM note_table WHERE _id = :taskId")
    suspend fun delete(taskId: Int): Int

    @Query("DELETE FROM note_table WHERE completed=1")
    suspend fun deleteAllCompletedNotes()

    @Query("SELECT * FROM note_table ORDER BY _id DESC LIMIT 1")
    suspend fun getLatestNotes(): Notes?




}