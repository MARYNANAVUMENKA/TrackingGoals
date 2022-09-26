package com.example.trackinggoals.domain.usecases.note

import com.example.trackinggoals.domain.repositories.NoteRepository

class UpdateTextGoalsNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(textGoals: String, idIm: Int)=repository.updateTextGoalsNote(textGoals, idIm)
}