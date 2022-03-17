package com.example.trackinggoals

import com.example.trackinggoals.model.Goals
import com.example.trackinggoals.model.NoteWithIncoming

interface Navigator {

    fun showNewNote(noteWithIncoming: NoteWithIncoming)

    fun goBaseMenu()

    fun showGoalsStepFirst(goalsId:Int)

    fun showGoalsStepSecond(goalsId:Int)

}