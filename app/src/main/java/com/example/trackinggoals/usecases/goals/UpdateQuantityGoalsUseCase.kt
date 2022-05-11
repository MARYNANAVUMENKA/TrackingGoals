package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class UpdateQuantityGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(quantity: Long, id: Int)=repository.updateQuantityGoals(quantity, id)
}