package com.example.trackinggoals

import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.GoalsRepository
import com.example.trackinggoals.model.RoomGoalsRepository

class MainActivityViewModel(
    private val goalsRepository: GoalsRepository
): ViewModel() {
}