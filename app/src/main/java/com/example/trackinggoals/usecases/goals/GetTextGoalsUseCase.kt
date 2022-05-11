package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class GetTextGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(textGoals: String)=repository.getTextGoals(textGoals)
}