package com.example.trackinggoals.usecases.note

import com.example.trackinggoals.repositories.NoteRepository

class UpdateTextGoalsNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(textGoals: String, idIm: Int)=repository.updateTextGoalsNote(textGoals, idIm)
}