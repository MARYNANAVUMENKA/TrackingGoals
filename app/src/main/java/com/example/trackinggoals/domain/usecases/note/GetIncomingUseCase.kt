package com.example.trackinggoals.domain.usecases.note

import com.example.trackinggoals.data.repositories.NoteRepository

class GetIncomingUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(incomingId:Int,noteId:Int,currentDataIn:String)=repository.getIncoming(incomingId,noteId,currentDataIn)
}