package com.example.trackinggoals

import android.view.View

interface Navigator {

    fun showNewNote(note: Note)

    fun goBack()

}