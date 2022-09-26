package com.example.trackinggoals.domain.model

data class Incoming(
    val idIm: Int,
    val idNote: Int,
    val currentDataIn: String,
    val textGoals: String,
    val quantity: String,
    val textMessages: String
)