package com.example.trackinggoals.domain.usecases.note

import com.example.trackinggoals.data.repositories.NoteRepository

class GetCurrentDayNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke()=repository.getCurrentDayNote()
}