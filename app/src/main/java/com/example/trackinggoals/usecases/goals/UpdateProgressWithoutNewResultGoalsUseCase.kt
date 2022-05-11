package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class UpdateProgressWithoutNewResultGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(progress: String, id: Int, text:String)=repository.updateProgressWithoutNewResultGoals(progress, id, text)
}