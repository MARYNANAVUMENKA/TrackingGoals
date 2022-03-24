package com.example.trackinggoals.model

data class Note(
    val id: Int,
    val currentData: String
)

data class Incoming(
    val idIm: Int,
    val idNote: Int,
    val currentDataIn: String,
    val idGoals:Int,
    val quantity:String,
    val textMessages: String
)

data class NoteIncoming(
    val note: Note,
    val listIncoming: List<Incoming>
)



