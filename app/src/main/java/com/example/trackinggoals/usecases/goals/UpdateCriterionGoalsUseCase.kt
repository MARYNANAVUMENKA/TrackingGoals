package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class UpdateCriterionGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(criterion: String, id: Int)=repository.updateCriterionGoals(criterion, id)
}