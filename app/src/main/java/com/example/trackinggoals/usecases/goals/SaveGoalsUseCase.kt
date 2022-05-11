package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class SaveGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(
        photo: String,
        textGoals: String,
        dataExecution: String,
        progress: Long,
        quantity: Long,
        unit: String,
        criterion: String
    ) = repository.saveGoals(photo, textGoals, dataExecution, progress, quantity, unit, criterion)
}