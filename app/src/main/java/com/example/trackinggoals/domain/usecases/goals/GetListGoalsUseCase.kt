package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.data.repositories.GoalsRepository

class GetListGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke()=repository.getListGoals()
}