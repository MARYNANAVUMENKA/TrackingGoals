package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.data.repositories.GoalsRepository

class GetListActiveGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke()=repository.getListActiveGoals()
}