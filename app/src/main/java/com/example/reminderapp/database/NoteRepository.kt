package com.example.reminderapp.database

import com.example.reminderapp.database.source.local.NoteDao


class NoteRepository(private val noteDao: NoteDao) {

    suspend fun insertNote(note: Notes) {
        noteDao.insertNote(note)
    }

    fun getNoteList() = noteDao.getNoteList()

    suspend fun updateNote(note: Notes) {
        noteDao.updateNote(note)
    }

    suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    fun getCompletedNotes() = noteDao.getCompletedNotes()

    suspend fun getNoteById(noteId: Int): Notes? {
        return noteDao.getNoteById(noteId)
    }

    suspend fun deleteAllCompletedNotes(){
        noteDao.deleteAllCompletedNotes()
    }
    fun getNoteLiveDataById(noteId: Int) = noteDao.getNoteLiveDataById(noteId)

    suspend fun delete(id:Int) {
        noteDao.delete(id)
    }

    suspend fun getLatestNotes(): Notes? {
        return noteDao.getLatestNotes()
    }

}


