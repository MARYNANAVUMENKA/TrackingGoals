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

    @Query("SELECT * FROM incoming WHERE id_im = :idIm")
    suspend fun findByIncomingId(idIm: Int): IncomingDbEntity

    @Insert(entity = IncomingDbEntity::class)
    suspend fun createIncoming(incomingDbEntity: IncomingDbEntity)

    @Query("SELECT * FROM incoming")
    fun getAllIncoming(): List<IncomingDbEntity>

    @Query("UPDATE incoming SET text_messages =:textMessages WHERE id_im = :idIm")
    fun updateTextMessage(textMessages: String, idIm: Int)

    @Query("UPDATE incoming SET text_goals =:textGoals WHERE id_im = :idIm")
    fun updateTextGoals(textGoals: String, idIm: Int)

    @Query("UPDATE incoming SET quantity =:quantity WHERE id_im = :idIm")
    fun updateQuantity(quantity: String, idIm: Int)


    @Transaction
    @Query("SELECT*FROM notes ")
    fun getNoteWithIncoming(): List<NoteWithIncoming>

    @Delete
    suspend fun deleteIncoming(incomingDbEntity: IncomingDbEntity)
}