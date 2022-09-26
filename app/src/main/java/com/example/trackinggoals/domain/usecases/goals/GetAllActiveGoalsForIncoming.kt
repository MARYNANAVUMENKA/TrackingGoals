package com.example.trackinggoals.domain.usecases.goals

import com.example.trackinggoals.data.repositories.GoalsRepository

class GetAllActiveGoalsForIncoming(private val repository: GoalsRepository) {
    suspend operator fun invoke()= repository.getAllActiveGoalsForIncoming()
}