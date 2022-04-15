package com.example.trackinggoals.model.incoming

import com.example.trackinggoals.model.notes.entities.Incoming
import com.example.trackinggoals.model.goals.entities.Goals
import java.util.ArrayList

interface IncomingRepository {

    suspend fun getIncoming(incomingId: Int, noteId: Int, currentDataIn: String): Incoming

    suspend fun updateTextIncoming(textMessages: String, idIm: Int)

    suspend fun updateProgress(progress: String, idGoals: Int, text: String)

    suspend fun updateTextGoals(textGoals: String, idIm: Int)

    suspend fun updateQuantity(quantity: String, idIm: Int)

    suspend fun deleteIncoming(incoming: Incoming)

    suspend fun getAllGoals(): ArrayList<String>

    suspend fun getGoals(textGoals: String): Goals
}