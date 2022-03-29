package com.example.trackinggoals

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.Goals
import com.example.trackinggoals.model.GoalsRepository
import kotlinx.coroutines.*

class BaseMenuViewModel(
    private val goalsRepository : GoalsRepository
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listGoalsLiveData = MutableLiveData<List<Goals>>()
    val listGoalsLiveData: LiveData<List<Goals>> = _listGoalsLiveData

    init {
        loadListGoals()
    }

    fun loadListGoals(){
        scope.launch {
            try {
                val listGoals = withContext(Dispatchers.IO) {
                    goalsRepository.getListGoals()
                }
                _listGoalsLiveData.value = listGoals
                Log.d("yyyy", _listGoalsLiveData.value.toString())
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