package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class UpdateDataExecutionGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(dataExecution: String, id: Int)=repository.updateDataExecutionGoals(dataExecution, id)
}