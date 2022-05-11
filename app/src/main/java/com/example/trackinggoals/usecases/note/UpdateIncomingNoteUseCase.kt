package com.example.trackinggoals.usecases.note

import com.example.trackinggoals.repositories.NoteRepository

class UpdateIncomingNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(textMessages: String, idIm: Int)=repository.updateIncomingNote(textMessages, idIm)
}