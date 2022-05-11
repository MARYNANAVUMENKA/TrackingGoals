package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class UpdateUnitGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(unit: String, id: Int)=repository.updateUnitGoals(unit, id)
}