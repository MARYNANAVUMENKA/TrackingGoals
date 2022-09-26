package com.example.trackinggoals.data.repositories

import com.example.trackinggoals.domain.model.Incoming
import com.example.trackinggoals.domain.model.Note
import com.example.trackinggoals.domain.model.NoteIncoming


interface NoteRepository {

    suspend fun getCurrentMonthYearNote(
        currentYear: Int,
        currentMonth: Int,
        currentDay: Int
    ): String

    suspend fun getListCurrentMonthYearNote(
        currentYear: Int,
        currentMonth: Int,
        currentDay: Int
    ): List<NoteIncoming>

    suspend fun getIncoming(incomingId: Int, noteId: Int, currentDataIn: String): Incoming

    suspend fun getCurrentDayNote(): Note

    suspend fun saveNoteWithIncoming(incoming: Incoming)

    suspend fun saveNoteWithIncomingFromGoals(
        progress: String,
        textGoals: String,
        note: Note,
        text: String
    )

    suspend fun updateIncomingNote(textMessages: String, idIm: Int)

    suspend fun updateTextGoalsNote(textGoals: String, idIm: Int)

    suspend fun updateQuantityNote(quantity: String, idIm: Int)

    suspend fun deleteIncomingNote(incoming: Incoming)
}