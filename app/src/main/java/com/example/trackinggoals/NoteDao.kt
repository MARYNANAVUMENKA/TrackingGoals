package com.example.trackinggoals

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(entity = NoteDbEntity::class)
    suspend fun createNote(noteDbEntity: NoteDbEntity)

    @Delete
    suspend fun deleteNote(noteDbEntity: NoteDbEntity)

    @Query("SELECT * FROM notes")
    fun getAllNotes(): List<NoteDbEntity>

    @Query("SELECT * FROM notes WHERE current_data = :currentData")
    suspend fun findByData(currentData: String): NoteDbEntity

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun findById(id: Int): NoteDbEntity

    @Query("SELECT * FROM incoming WHERE id_note = :idNote")
    suspend fun findByNoteId(idNote: Int): IncomingDbEntity

    @Insert(entity = IncomingDbEntity::class)
    suspend fun createIncomingMessage(incomingDbEntity: IncomingDbEntity)

    @Delete
    suspend fun deleteIncomingMessage(incomingDbEntity: IncomingDbEntity)

    @Query("SELECT * FROM incoming")
    fun getAllIncoming(): List<IncomingDbEntity>

    @Transaction
    @Query("SELECT*FROM notes ")
    fun getNotewithIncoming(): List<NoteWithIncoming>
}