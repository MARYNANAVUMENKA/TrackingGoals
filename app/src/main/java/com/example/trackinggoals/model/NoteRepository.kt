package com.example.trackinggoals.model

interface NoteRepository {

    suspend fun getCurrentMonthYear(currentYear:Int,currentMonth:Int,currentDay:Int):String

    suspend fun getListCurrentMonthYear(currentYear:Int, currentMonth:Int, currentDay:Int): List<NoteIncoming>

    suspend fun getIncoming(incomingId:Int,noteId:Int,currentDataIn:String): Incoming

    suspend fun getCurrentDay():Note

    suspend fun saveNoteWithIncoming(incoming: Incoming)

    suspend fun saveNoteWithIncomingFromGoals(progress:String,textGoals: String,note: Note)

    suspend fun updateIncoming(textMessages: String, idIm: Int)

    suspend fun deleteIncoming(incoming: Incoming)
}