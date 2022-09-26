package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.domain.repositories.GoalsRepository

class GetIdGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(id:Int)=repository.getIdGoals(id)
}