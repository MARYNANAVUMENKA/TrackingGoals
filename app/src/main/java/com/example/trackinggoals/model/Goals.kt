package com.example.trackinggoals.model

data class Goals(
    val id: Int,
    val isActive:Boolean,
    val photo:String,
    val textGoals:String,
    val dataExecution: String,
    val progress:Int,
    val quantity:Int,
    val unit:String,
    val criterion:String
)