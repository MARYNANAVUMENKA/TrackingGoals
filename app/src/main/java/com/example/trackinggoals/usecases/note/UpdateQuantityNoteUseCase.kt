package com.example.trackinggoals.usecases.note

import com.example.trackinggoals.repositories.NoteRepository

class UpdateQuantityNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(quantity: String, idIm: Int)=repository.updateQuantityNote(quantity, idIm)
}