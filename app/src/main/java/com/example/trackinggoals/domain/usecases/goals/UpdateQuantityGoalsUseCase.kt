package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.domain.repositories.GoalsRepository

class UpdateQuantityGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(quantity: Long, id: Int)=repository.updateQuantityGoals(quantity, id)
}