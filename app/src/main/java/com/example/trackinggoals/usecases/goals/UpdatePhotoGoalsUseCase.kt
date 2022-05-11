package com.example.trackinggoals.usecases.goals

import com.example.trackinggoals.repositories.GoalsRepository

class UpdatePhotoGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(photo: String, id: Int)=repository.updatePhotoGoals(photo, id)
}