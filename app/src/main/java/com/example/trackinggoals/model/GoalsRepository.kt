package com.example.trackinggoals.model

interface GoalsRepository {

    suspend fun getListGoals(): List<Goals>

    suspend fun getIdGoals(id:Int): Goals

    suspend fun saveGoals(id:Int,textGoals:String)

    suspend fun editGoalsText(id:Int,textGoals:String)
}