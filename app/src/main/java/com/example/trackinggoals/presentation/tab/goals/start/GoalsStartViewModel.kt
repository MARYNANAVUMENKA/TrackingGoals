package com.example.trackinggoals.presentation.tab.goals.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.domain.model.Goals
import com.example.trackinggoals.domain.usecases.goals.GetListActiveGoalsUseCase
import kotlinx.coroutines.*

class GoalsStartViewModel(
    private val getListActiveGoalsUseCase:GetListActiveGoalsUseCase

) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listGoalsLiveData = MutableLiveData<List<Goals>>()
    val listGoalsLiveData: LiveData<List<Goals>>
        get() = _listGoalsLiveData

    init {
        getListActiveGoals()
    }

    private fun getListActiveGoals() {
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

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }
}