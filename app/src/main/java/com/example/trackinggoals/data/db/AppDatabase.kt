package com.example.trackinggoals.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trackinggoals.data.room.entities.QuoteDbEntity
import com.example.trackinggoals.data.room.entities.GoalsDbEntity
import com.example.trackinggoals.data.room.entities.IncomingDbEntity
import com.example.trackinggoals.data.room.entities.NoteDbEntity

@Database(
    version = 1,
    entities = [NoteDbEntity::class, IncomingDbEntity::class, GoalsDbEntity::class, QuoteDbEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    abstract fun getGoalsDao(): GoalsDao

    abstract fun getQuotesDao(): QuoteDao
}