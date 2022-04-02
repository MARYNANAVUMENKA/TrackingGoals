package com.example.trackinggoals.model.notes

import com.example.trackinggoals.model.notes.entities.Incoming
import com.example.trackinggoals.model.notes.entities.Note
import com.example.trackinggoals.model.notes.entities.NoteIncoming

interface NoteRepository {

    suspend fun getCurrentMonthYear(currentYear:Int,currentMonth:Int,currentDay:Int):String

    suspend fun getListCurrentMonthYear(currentYear:Int, currentMonth:Int, currentDay:Int): List<NoteIncoming>

    suspend fun getIncoming(incomingId:Int,noteId:Int,currentDataIn:String): Incoming

    suspend fun getCurrentDay(): Note

    suspend fun saveNoteWithIncoming(incoming: Incoming)

    suspend fun saveNoteWithIncomingFromGoals(progress:String,textGoals: String,note: Note)

    suspend fun updateIncoming(textMessages: String, idIm: Int)

    suspend fun updateTextGoals(textGoals: String, idIm: Int)

    suspend fun updateQuantity(quantity: String, idIm: Int)

    suspend fun deleteIncoming(incoming: Incoming)
}