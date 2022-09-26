package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.domain.repositories.GoalsRepository

class UpdateTextGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(textGoals: String, id: Int)=repository.updateTextGoals(textGoals, id)
}