package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.data.repositories.GoalsRepository

class GetTextGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(textGoals: String)=repository.getTextGoals(textGoals)
}