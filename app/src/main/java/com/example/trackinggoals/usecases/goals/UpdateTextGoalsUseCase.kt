package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class UpdateTextGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(textGoals: String, id: Int)=repository.updateTextGoals(textGoals, id)
}