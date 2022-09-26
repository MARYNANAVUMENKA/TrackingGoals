package com.example.trackinggoals.domain.usecases.note

import com.example.trackinggoals.domain.repositories.NoteRepository

class UpdateIncomingNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(textMessages: String, idIm: Int)=repository.updateIncomingNote(textMessages, idIm)
}