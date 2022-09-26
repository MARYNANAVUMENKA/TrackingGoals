package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.data.repositories.GoalsRepository

class UpdateProgressWithoutNewResultGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(progress: String, id: Int, text:String)=repository.updateProgressWithoutNewResultGoals(progress, id, text)
}