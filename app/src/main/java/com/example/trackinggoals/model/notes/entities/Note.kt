package com.example.trackinggoals.model.notes.entities

data class Note(
    val id: Int,
    val currentData: String,
    var isToday:Boolean
)

data class Incoming(
    val idIm: Int,
    val idNote: Int,
    val currentDataIn: String,
    val textGoals: String,
    val quantity: String,
    val textMessages: String
)

data class NoteIncoming(
    val note: Note,
    val listIncoming: List<Incoming>
)



