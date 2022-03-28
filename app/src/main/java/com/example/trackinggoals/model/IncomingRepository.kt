package com.example.trackinggoals.model

import java.util.ArrayList

interface IncomingRepository {

    suspend fun getIncoming(incomingId: Int, noteId: Int, currentDataIn: String): Incoming

    suspend fun updateTextIncoming(textMessages: String, idIm: Int)

    suspend fun updateProgress(progress:String,idGoals:Int)

    suspend fun deleteIncoming(incoming: Incoming)

    suspend fun getAllGoals():ArrayList<String>

    suspend fun getGoals(textGoals: String):Goals
}