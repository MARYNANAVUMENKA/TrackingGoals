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

class GoalsConstructorViewModel(
    private val goalsRepository : GoalsRepository
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _idGoals = MutableLiveData<Int>()
    val idGoals: LiveData<Int> = _idGoals


    fun createEmptyGoals() {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.createGoals()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun getIdEmptyGoals() {
        scope.launch {
            try {
                val listId=withContext(Dispatchers.IO) {
                    goalsRepository.getAllId()
                }
                _idGoals.value = listId[0]
                Log.d("gggg", _idGoals.value.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



}