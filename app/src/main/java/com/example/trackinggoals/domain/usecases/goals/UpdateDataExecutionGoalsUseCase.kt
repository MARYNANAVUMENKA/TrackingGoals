package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.data.repositories.GoalsRepository

class UpdateDataExecutionGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(dataExecution: String, id: Int)=repository.updateDataExecutionGoals(dataExecution, id)
}