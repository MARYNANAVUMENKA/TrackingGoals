package com.example.trackinggoals.domain.usecases.note

import com.example.trackinggoals.domain.repositories.NoteRepository

class UpdateQuantityNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(quantity: String, idIm: Int)=repository.updateQuantityNote(quantity, idIm)
}