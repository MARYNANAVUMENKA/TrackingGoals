package com.example.trackinggoals.usecases.note

import com.example.trackinggoals.repositories.NoteRepository

class GetListCurrentMonthYearNoteUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(currentYear:Int, currentMonth:Int, currentDay:Int)=repository.getListCurrentMonthYearNote(currentYear, currentMonth, currentDay)
}