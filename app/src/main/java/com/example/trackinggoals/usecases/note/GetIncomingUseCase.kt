package com.example.trackinggoals.usecases.note

import com.example.trackinggoals.repositories.NoteRepository

class GetIncomingUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(incomingId:Int,noteId:Int,currentDataIn:String)=repository.getIncoming(incomingId,noteId,currentDataIn)
}