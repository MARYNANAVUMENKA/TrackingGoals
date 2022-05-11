package com.example.trackinggoals.screens.tab.goals.list.active

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.goals.entities.Goals
import com.example.trackinggoals.usecases.goals.GetListActiveGoalsUseCase
import com.example.trackinggoals.usecases.goals.RemoveGoalsUseCase
import com.example.trackinggoals.usecases.goals.UpdateIsActiveGoalsUseCase
import com.example.trackinggoals.usecases.goals.UpdateProgressGoalsUseCase
import kotlinx.coroutines.*

class GoalsListActiveViewModel(
    private val getListActiveGoalsUseCase:GetListActiveGoalsUseCase,
    private val updateIsActiveGoalsUseCase:UpdateIsActiveGoalsUseCase,
    private val removeGoalsUseCase:RemoveGoalsUseCase,
    private val updateProgressGoalsUseCase: UpdateProgressGoalsUseCase,

    ) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listGoalsLiveData = MutableLiveData<List<Goals>>()
    val listGoalsLiveData: LiveData<List<Goals>>
        get() = _listGoalsLiveData

    init {
        getListActiveGoals()
    }

    fun getListActiveGoals() {
        scope.launch {
            try {
                val listGoals = withContext(Dispatchers.IO) {
                    getListActiveGoalsUseCase.invoke()
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
                    updateIsActiveGoalsUseCase.invoke(isActive, id)
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
                    removeGoalsUseCase.invoke(id)
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
                    updateProgressGoalsUseCase.invoke(progress, id, text)
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