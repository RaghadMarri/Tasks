package com.example.reminderapp.database


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

    suspend fun getNoteById(noteId: String): Notes? {
        return noteDao.getNoteById(noteId)
    }

    fun getNoteLiveDataById(noteId: String) = noteDao.getNoteLiveDataById(noteId)

    suspend fun delete(note: Notes) {
        noteDao.delete(note)
    }

    suspend fun getLatestNotes(): Notes? {
        return noteDao.getLatestNotes()
    }

}


