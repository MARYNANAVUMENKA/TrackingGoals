package com.example.trackinggoals.usecases.note

import com.example.trackinggoals.model.notes.entities.Note
import com.example.trackinggoals.repositories.NoteRepository

class SaveNoteWithIncomingFromGoalsUseCase(private val repository: NoteRepository) {
    suspend operator fun invoke(progress:String, textGoals: String, note: Note, text:String)=repository.saveNoteWithIncomingFromGoals(progress,textGoals,note,text)
}