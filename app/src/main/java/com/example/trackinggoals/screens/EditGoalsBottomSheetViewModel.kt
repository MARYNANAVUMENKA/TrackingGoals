package com.example.trackinggoals.screens

import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.GoalsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditGoalsBottomSheetViewModel(
    private  var goalsRepository: GoalsRepository
): ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    fun removeGoals(){
        scope.launch {
            try {
                val listGoals = withContext(Dispatchers.IO) {
                    goalsRepository.getListGoals()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}