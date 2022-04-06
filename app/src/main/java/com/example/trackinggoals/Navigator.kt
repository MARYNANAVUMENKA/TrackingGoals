package com.example.trackinggoals

import com.example.trackinggoals.model.notes.entities.Incoming
import com.example.trackinggoals.model.notes.entities.NoteIncoming

interface Navigator {

    fun showIncoming(Incoming: Incoming)

    fun showIncoming(noteIncoming: NoteIncoming)

    fun showNewNoteIncoming(noteIncoming: NoteIncoming)

    fun goBack()

    fun goBaseMenu()

    fun showGoalsConstructor(goalsId: Int)


}