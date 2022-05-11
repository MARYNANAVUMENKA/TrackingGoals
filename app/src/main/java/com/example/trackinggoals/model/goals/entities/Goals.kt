package com.example.trackinggoals.model.goals.entities

data class Goals(
    val id: Int,
    val isActive: Boolean,
    val photo: String,
    val textGoals: String,
    val dataExecution: String,
    val progress: Long,
    val quantity: Long,
    val unit: String,
    val criterion: String
)