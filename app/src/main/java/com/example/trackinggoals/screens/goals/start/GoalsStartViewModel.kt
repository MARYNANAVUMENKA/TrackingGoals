package com.example.trackinggoals.screens.goals.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.goals.GoalsRepository
import kotlinx.coroutines.*

class GoalsStartViewModel(
    private val goalsRepository: GoalsRepository
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)
    private val _idGoals = MutableLiveData<Int>()
    val idGoals: LiveData<Int> = _idGoals

    fun createEmptyGoals() {
        scope.launch {
            try {
                val id = withContext(Dispatchers.IO) {
                    goalsRepository.createGoals()
                }
                _idGoals.value = id

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}