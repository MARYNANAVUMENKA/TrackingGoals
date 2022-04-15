package com.example.trackinggoals.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trackinggoals.model.quotes.room.QuoteDao
import com.example.trackinggoals.model.quotes.room.entities.QuoteDbEntity
import com.example.trackinggoals.model.goals.room.GoalsDao
import com.example.trackinggoals.model.goals.room.entities.GoalsDbEntity
import com.example.trackinggoals.model.notes.NoteDao
import com.example.trackinggoals.model.notes.room.entitie.IncomingDbEntity
import com.example.trackinggoals.model.notes.room.entitie.NoteDbEntity

@Database(
    version = 1,
    entities = [NoteDbEntity::class, IncomingDbEntity::class, GoalsDbEntity::class, QuoteDbEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    abstract fun getGoalsDao(): GoalsDao

    abstract fun getQuotes(): QuoteDao
}