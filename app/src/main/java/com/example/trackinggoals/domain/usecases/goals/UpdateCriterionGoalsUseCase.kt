package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.data.repositories.GoalsRepository

class UpdateCriterionGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(criterion: String, id: Int)=repository.updateCriterionGoals(criterion, id)
}