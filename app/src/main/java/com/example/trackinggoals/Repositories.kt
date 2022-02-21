package com.example.trackinggoals

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy<AppDatabase> {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .build()
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    val noteRepository: NoteRepository by lazy {
        RoomNoteRepository(database.getNoteDao(), ioDispatcher)
    }

    fun init(context: Context) {
        applicationContext = context
    }
}



