package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.data.repositories.GoalsRepository

class UpdatePhotoGoalsUseCase(private val repository: GoalsRepository) {
    suspend operator fun invoke(photo: String, id: Int)=repository.updatePhotoGoals(photo, id)
}