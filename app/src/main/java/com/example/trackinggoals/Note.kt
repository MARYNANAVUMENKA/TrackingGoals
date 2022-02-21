package com.example.trackinggoals

import java.util.*

data class Note(
    val id: Int,
    val currentData: String
)

data class Incoming(
    val idIm: Int,
    val idNote: Int,
    val textMessages: String
)

data class NoteIncoming(
    val note:Note,
    val listIncoming: List<Incoming>?=null
)