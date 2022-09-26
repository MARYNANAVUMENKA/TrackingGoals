package com.example.trackinggoals.domain.model

data class NoteIncoming(
    val note: Note,
    val listIncoming: List<Incoming>
)