package com.example.trackinggoals.model

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

    @Query("SELECT * FROM incoming")
    fun getAllIncoming(): List<IncomingDbEntity>

    @Query("UPDATE incoming SET text_messages =:textMessages WHERE id_note = :idNote")
    fun update(textMessages: String, idNote: Int)

    @Transaction
    @Query("SELECT*FROM notes ")
    fun getNotewithIncoming(): List<NoteWithIncoming>

    @Delete
    suspend fun deleteNoteWithIncoming(noteDbEntity: NoteDbEntity, list : List<IncomingDbEntity>)
}