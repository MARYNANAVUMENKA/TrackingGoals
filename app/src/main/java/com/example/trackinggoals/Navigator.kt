package com.example.trackinggoals

import com.example.trackinggoals.model.Goals
import com.example.trackinggoals.model.NoteWithIncoming

interface Navigator {

    fun showNewNote(noteWithIncoming: NoteWithIncoming)

    fun goBack()

    fun goBaseMenu()

    fun showGoalsConstructor(goalsId: Int)


}