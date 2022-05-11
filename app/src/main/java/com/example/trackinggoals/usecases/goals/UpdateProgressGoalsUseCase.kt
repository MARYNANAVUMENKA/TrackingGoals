package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository
import com.example.trackinggoals.repositories.NoteRepository

class UpdateProgressGoalsUseCase(private val goalsRepository: GoalsRepository, private val noteRepository: NoteRepository) {
    suspend operator fun invoke(progress: String, id: Int, text:String) {
        goalsRepository.updateProgressGoals(progress, id, text)
        val goalsDbEntity = goalsRepository.getIdGoals(id)
        val textGoalsDbEntity = goalsDbEntity.textGoals
        val note = noteRepository.getCurrentDayNote()
        if (progress.contains("-")) {
            noteRepository.saveNoteWithIncomingFromGoals(progress, textGoalsDbEntity, note, text)
        } else {
            noteRepository.saveNoteWithIncomingFromGoals(progress, textGoalsDbEntity, note, text)
        }
    }
}