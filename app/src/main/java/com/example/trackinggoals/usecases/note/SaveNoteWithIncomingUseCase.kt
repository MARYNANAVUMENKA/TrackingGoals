package com.example.trackinggoals.usecases.note

import com.example.trackinggoals.model.notes.entities.Incoming
import com.example.trackinggoals.repositories.NoteRepository

class SaveNoteWithIncomingUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(incoming: Incoming)=repository.saveNoteWithIncoming(incoming)
}