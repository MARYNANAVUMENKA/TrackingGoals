package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class RemoveGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(id:Int)=repository.removeGoals(id)
}