package com.example.trackinggoals.model

data class Note(
    val id: Int,
    val currentData: String
)

data class Incoming(
    val idIm: Int,
    val idNote: Int,
    val textMessages: String
)



