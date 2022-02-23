package com.example.trackinggoals

interface NoteRepository {

    suspend fun getCurrentMonthYear(currentYear:Int,currentMonth:Int,currentDay:Int):String

    suspend fun getListCurrentMonthYear(currentYear:Int, currentMonth:Int, currentDay:Int): List<NoteWithIncoming>

    suspend fun getIdNote(noteId:Int,currentData: String):Incoming

    suspend fun getCurrentDay(noteId:Int):String

    suspend fun getlistNoteWithIncoming(): List<NoteWithIncoming>

    suspend fun saveNoteWithIncoming(text:String,noteId:Int,currentData: String)
}