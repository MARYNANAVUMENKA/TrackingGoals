package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class GetListAchievedGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke()=repository.getListAchievedGoals()
}