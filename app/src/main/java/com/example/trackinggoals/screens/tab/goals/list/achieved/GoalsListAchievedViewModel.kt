package com.example.trackinggoals.screens.tab.goals.list.achieved

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.goals.entities.Goals
import com.example.trackinggoals.model.goals.GoalsRepository
import kotlinx.coroutines.*

class GoalsListAchievedViewModel(
    private var goalsRepository: GoalsRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listGoalsLiveData = MutableLiveData<List<Goals>>()
    val listGoalsLiveData: LiveData<List<Goals>> = _listGoalsLiveData

    init {
        getListActiveGoals()
    }

    private fun getListActiveGoals() {
        scope.launch {
            try {
                val listGoals = withContext(Dispatchers.IO) {
                    goalsRepository.getListAchievedGoals()
                }
                _listGoalsLiveData.value = listGoals
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun editStatusGoals(isActive: Boolean, id: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.updateIsActive(isActive, id)
                }
                getListActiveGoals()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun removeGoals(id: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.removeGoals(id)
                }
                getListActiveGoals()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateProgress(progress: String, id: Int, text: String) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.updateProgress(progress, id, text)
                }
                getListActiveGoals()
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