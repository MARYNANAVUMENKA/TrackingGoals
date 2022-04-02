package com.example.trackinggoals.model

interface GoalsRepository {

    suspend fun getListGoals(): List<Goals>

    suspend fun getIdGoals(id:Int): Goals

    suspend fun getTextGoals(textGoals: String):Goals

    suspend fun createGoals():Int

    suspend fun removeGoals(id:Int)

    suspend fun updateText(textGoals: String, id: Int)

    suspend fun updatePhoto(photo: String, id: Int)

    suspend fun updateDataExecution(dataExecution: String, id: Int)

    suspend fun updateIsActive(isActive: Boolean, id: Int)

    suspend fun updateProgress(progress: String, id: Int)

    suspend fun updateProgressWithoutNewResult(progress: String, id: Int)

    suspend fun updateQuantity(quantity: Int, id: Int)

    suspend fun updateUnit(unit: String, id: Int)

    suspend fun updateCriterion(criterion: String, id: Int)

}