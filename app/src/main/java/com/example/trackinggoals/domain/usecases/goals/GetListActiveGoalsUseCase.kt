package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.domain.repositories.GoalsRepository

class GetListActiveGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke()=repository.getListActiveGoals()
}