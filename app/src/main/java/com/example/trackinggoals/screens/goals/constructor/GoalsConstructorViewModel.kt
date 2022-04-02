package com.example.trackinggoals.screens.goals.constructor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.goals.GoalsRepository
import kotlinx.coroutines.*

class GoalsConstructorViewModel(
    private val goalsRepository: GoalsRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String> = _photo

    private val _textGoals = MutableLiveData<String>()
    val textGoals: LiveData<String> = _textGoals

    private val _dataExecution = MutableLiveData<String>()
    val dataExecution: LiveData<String> = _dataExecution

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress

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
                _photo.value = goals.photo
                _textGoals.value = goals.textGoals
                _dataExecution.value = goals.dataExecution
                _progress.value = goals.progress
                _quantity.value = goals.quantity
                _unit.value = goals.unit
                _criterion.value = goals.criterion
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTextGoals(textGoals: String, id: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.updateText(textGoals, id)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updatePhotoGoals(photo: String, id: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.updatePhoto(photo, id)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateDataGoals(data: String, id: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.updateDataExecution(data, id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantityGoals(quantity: Int, id: Int) {
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

    fun updateUnitGoals(unit: String, id: Int) {
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

    fun updateCriterionGoals(criterion: String, id: Int) {
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

    fun deleteGoals(id: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    goalsRepository.removeGoals(id)
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