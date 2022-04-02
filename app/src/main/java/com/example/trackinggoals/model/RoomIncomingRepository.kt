package com.example.trackinggoals.model

import java.util.ArrayList

class RoomIncomingRepository(
    private val noteRepository: NoteRepository,
    private val goalsRepository: GoalsRepository
):IncomingRepository {

    override suspend fun getIncoming(
        incomingId: Int,
        noteId: Int,
        currentDataIn: String
    ): Incoming {
        return noteRepository.getIncoming(incomingId, noteId, currentDataIn)
    }

    override suspend fun updateTextIncoming(textMessages: String, idIm: Int) {
        noteRepository.updateIncoming(textMessages,idIm)
    }

    override suspend fun updateProgress(progress: String, idGoals: Int) {
        goalsRepository.updateProgressWithoutNewResult(progress,idGoals)
    }

    override suspend fun updateTextGoals(textGoals: String, idIm: Int) {
        noteRepository.updateTextGoals(textGoals, idIm)
    }

    override suspend fun updateQuantity(quantity: String, idIm: Int) {
        noteRepository.updateQuantity(quantity, idIm)
    }

    override suspend fun deleteIncoming(incoming: Incoming) {
        noteRepository.deleteIncoming(incoming)
    }

    override suspend fun getAllGoals(): ArrayList<String> {
        val listGoals=goalsRepository.getListGoals()
        return if (listGoals.isNotEmpty()){
            listGoals.map { goals: Goals -> goals.textGoals } as ArrayList<String>
        }else{
            emptyList<String>() as ArrayList<String>
        }
    }

    override suspend fun getGoals(textGoals: String): Goals {
        return goalsRepository.getTextGoals(textGoals)
    }
}