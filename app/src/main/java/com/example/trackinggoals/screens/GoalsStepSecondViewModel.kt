package com.example.trackinggoals.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.GoalsRepository
import kotlinx.coroutines.*

class GoalsStepSecondViewModel(
    private val goalsRepository : GoalsRepository
): ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _quantity = MutableLiveData<String>()
    val quantity: LiveData<String> = _quantity

    private val _unit = MutableLiveData<String>()
    val unit: LiveData<String> = _unit

    private val _criterion = MutableLiveData<String>()
    val criterion: LiveData<String> = _criterion

    fun loadGoals(id: Int) {
        scope.launch {
            try {
                val goals = withContext(Dispatchers.IO) {
                    goalsRepository.getIdGoals(id)
                }
                _quantity.value = goals.photo
                _unit.value = goals.textGoals
                _criterion.value = goals.dataExecution
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