package com.example.trackinggoals.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.GoalsRepository
import kotlinx.coroutines.*
import kotlin.math.tanh

class GoalsStepFirstViewModel(
    private val goalsRepository : GoalsRepository
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.Main)

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String> = _photo

    private val _textGoals = MutableLiveData<String>()
    val textGoals: LiveData<String> = _textGoals

    private val _dataExecution = MutableLiveData<String>()
    val dataExecution: LiveData<String> = _dataExecution

    fun loadGoals(id: Int) {
        scope.launch {
            try {
                val goals = withContext(Dispatchers.IO) {
                    goalsRepository.getIdGoals(id)
                }
                _photo.value = goals.photo
                _textGoals.value = goals.textGoals
                _dataExecution.value = goals.dataExecution
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun saveGoals(id:Int,textGoals:String,){
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.saveGoals(id,textGoals)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun editGoals(id:Int,textGoals:String){
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.editGoalsText(id,textGoals)
                }

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