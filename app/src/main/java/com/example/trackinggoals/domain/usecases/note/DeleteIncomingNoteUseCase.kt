package com.example.trackinggoals.domain.usecases.note


import com.example.trackinggoals.data.repositories.NoteRepository
import com.example.trackinggoals.domain.model.Incoming

class DeleteIncomingNoteUseCase(private val repository:NoteRepository) {
    suspend operator fun invoke(incoming: Incoming)=repository.deleteIncomingNote(incoming)
}