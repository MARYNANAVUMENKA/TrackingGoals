package com.example.trackinggoals.model

import android.content.Context
import androidx.room.Room
import com.example.trackinggoals.model.quotes.QuoteRepository
import com.example.trackinggoals.model.quotes.room.RoomQuoteRepository
import com.example.trackinggoals.model.goals.GoalsRepository
import com.example.trackinggoals.model.goals.room.RoomGoalsRepository
import com.example.trackinggoals.model.incoming.IncomingRepository
import com.example.trackinggoals.model.incoming.room.RoomIncomingRepository
import com.example.trackinggoals.model.notes.NoteRepository
import com.example.trackinggoals.model.notes.room.RoomNoteRepository
import com.example.trackinggoals.model.room.AppDatabase

object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .build()
    }

    val noteRepository: NoteRepository by lazy {
        RoomNoteRepository(database.getNoteDao())
    }

    val goalsRepository: GoalsRepository by lazy {
        RoomGoalsRepository(noteRepository, database.getGoalsDao())
    }

    val incomingRepository: IncomingRepository by lazy {
        RoomIncomingRepository(noteRepository, goalsRepository)
    }

    val quoteRepository: QuoteRepository by lazy {
        RoomQuoteRepository(database.getQuotes())
    }

    fun init(context: Context) {
        applicationContext = context
    }
}



