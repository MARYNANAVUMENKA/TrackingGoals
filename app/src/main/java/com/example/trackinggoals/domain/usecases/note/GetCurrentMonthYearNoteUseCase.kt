package com.example.trackinggoals.domain.usecases.note

import com.example.trackinggoals.domain.repositories.NoteRepository

class GetCurrentMonthYearNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(currentYear:Int, currentMonth:Int, currentDay:Int)=repository.getCurrentMonthYearNote(currentYear, currentMonth, currentDay)
}