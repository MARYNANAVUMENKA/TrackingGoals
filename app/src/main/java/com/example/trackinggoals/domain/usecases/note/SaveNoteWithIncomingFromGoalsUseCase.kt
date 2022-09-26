package com.example.trackinggoals.domain.usecases.note


import com.example.trackinggoals.domain.repositories.NoteRepository
import com.example.trackinggoals.domain.model.Note

class SaveNoteWithIncomingFromGoalsUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(progress:String, textGoals: String, note: Note, text:String)=repository.saveNoteWithIncomingFromGoals(progress,textGoals,note,text)
}