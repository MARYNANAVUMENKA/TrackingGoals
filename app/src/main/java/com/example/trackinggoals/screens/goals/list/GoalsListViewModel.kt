package com.example.trackinggoals.screens.goals.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.goals.entities.Goals
import com.example.trackinggoals.model.goals.GoalsRepository
import kotlinx.coroutines.*

class GoalsListViewModel(
    private var goalsRepository: GoalsRepository
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _idGoals = MutableLiveData<Int>()
    val idGoals: LiveData<Int> = _idGoals

    private val _listGoalsLiveData = MutableLiveData<List<Goals>>()
    val listGoalsLiveData: LiveData<List<Goals>> = _listGoalsLiveData

    init {
        loadListGoals()
    }

    private fun loadListGoals() {
        scope.launch {
            try {
                val listGoals = withContext(Dispatchers.IO) {
                    goalsRepository.getListGoals()
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
                val listGoals = withContext(Dispatchers.IO) {
                    goalsRepository.getListGoals()
                }
                _listGoalsLiveData.value = listGoals
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
                val listGoals = withContext(Dispatchers.IO) {
                    goalsRepository.getListGoals()
                }
                _listGoalsLiveData.value = listGoals
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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

    fun updateProgress(progress: String, id: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.updateProgress(progress, id)
                }
                val listGoals = withContext(Dispatchers.IO) {
                    goalsRepository.getListGoals()
                }
                _listGoalsLiveData.value = listGoals
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