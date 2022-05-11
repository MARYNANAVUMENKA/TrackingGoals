package com.example.trackinggoals.repositories

import com.example.trackinggoals.model.goals.entities.Goals

interface GoalsRepository {

    suspend fun getListActiveGoals(): List<Goals>

    suspend fun getListAchievedGoals(): List<Goals>

    suspend fun getListGoals(): List<Goals>

    suspend fun getIdGoals(id: Int): Goals

    suspend fun getTextGoals(textGoals: String): Goals

    suspend fun saveGoals(
        photo: String,
        textGoals: String,
        dataExecution: String,
        progress: Long,
        quantity: Long,
        unit: String,
        criterion: String
    )

    suspend fun removeGoals(id: Int)

    suspend fun updateTextGoals(textGoals: String, id: Int)

    suspend fun updatePhotoGoals(photo: String, id: Int)

    suspend fun updateDataExecutionGoals(dataExecution: String, id: Int)

    suspend fun updateIsActiveGoals(isActive: Boolean, id: Int)

    suspend fun updateProgressGoals(progress: String, id: Int, text: String)

    suspend fun updateProgressWithoutNewResultGoals(progress: String, id: Int, text: String)

    suspend fun updateQuantityGoals(quantity: Long, id: Int)

    suspend fun updateUnitGoals(unit: String, id: Int)

    suspend fun updateCriterionGoals(criterion: String, id: Int)

    suspend fun getAllActiveGoalsForIncoming(): ArrayList<String>

}