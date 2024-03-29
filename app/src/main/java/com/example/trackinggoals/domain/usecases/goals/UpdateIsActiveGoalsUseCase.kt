package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.domain.repositories.GoalsRepository

class UpdateIsActiveGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(isActive: Boolean, id: Int)=repository.updateIsActiveGoals(isActive, id)
}