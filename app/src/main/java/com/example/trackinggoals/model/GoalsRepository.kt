package com.example.trackinggoals.model

interface GoalsRepository {

    suspend fun getListGoals(): List<Goals>

    suspend fun getAllId():List<Int>

    suspend fun getIdGoals(id:Int): Goals

    suspend fun createGoals()

    suspend fun updateText(textGoals: String, id: Int)

    suspend fun updatePhoto(photo: String, id: Int)

    suspend fun updateDataExecution(dataExecution: String, id: Int)



}