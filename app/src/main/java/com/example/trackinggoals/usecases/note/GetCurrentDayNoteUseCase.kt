package com.example.trackinggoals.usecases.note

import com.example.trackinggoals.repositories.NoteRepository

class GetCurrentDayNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke()=repository.getCurrentDayNote()
}