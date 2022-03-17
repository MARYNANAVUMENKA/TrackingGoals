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

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

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
                _quantity.value = goals.quantity
                _unit.value = goals.unit
                _criterion.value = goals.criterion
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateQuantityGoals(quantity:Int,id:Int){
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.updateQuantity(quantity, id)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateUnitGoals(unit:String,id:Int){
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.updateUnit(unit, id)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updateCriterionGoals(criterion:String,id:Int){
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.updateCriterion(criterion, id)
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