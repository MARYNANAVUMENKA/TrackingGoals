package com.example.trackinggoals.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [NoteDbEntity::class, IncomingDbEntity::class, GoalsDbEntity::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    abstract fun getGoalsDao(): GoalsDao

}