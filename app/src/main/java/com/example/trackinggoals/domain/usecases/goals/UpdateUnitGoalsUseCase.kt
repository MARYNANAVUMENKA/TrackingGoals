package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.domain.repositories.GoalsRepository

class UpdateUnitGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(unit: String, id: Int)=repository.updateUnitGoals(unit, id)
}