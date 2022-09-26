package com.example.trackinggoals.domain.usecases.note


import com.example.trackinggoals.data.repositories.NoteRepository
import com.example.trackinggoals.domain.model.Incoming

class SaveNoteWithIncomingUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(incoming: Incoming)=repository.saveNoteWithIncoming(incoming)
}