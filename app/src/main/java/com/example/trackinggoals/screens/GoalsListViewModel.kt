package com.example.trackinggoals.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.Goals
import com.example.trackinggoals.model.GoalsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GoalsListViewModel(
    private  var goalsRepository: GoalsRepository
):ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _listGoalsLiveData = MutableLiveData<List<Goals>>()
    val listGoalsLiveData: LiveData<List<Goals>> = _listGoalsLiveData

    fun loadListGoals(){
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
}