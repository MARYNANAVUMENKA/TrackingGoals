package com.example.trackinggoals.screens.constructor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.usecases.goals.*
import kotlinx.coroutines.*

class GoalsConstructorViewModel(
    private val getIdGoalsUseCase: GetIdGoalsUseCase,
    private val saveGoalsUseCase: SaveGoalsUseCase,
    private val updateTextGoalsUseCase: UpdateTextGoalsUseCase,
    private val updatePhotoGoalsUseCase: UpdatePhotoGoalsUseCase,
    private val updateDataExecutionGoalsUseCase: UpdateDataExecutionGoalsUseCase,
    private val updateQuantityGoalsUseCase: UpdateQuantityGoalsUseCase,
    private val updateUnitGoalsUseCase: UpdateUnitGoalsUseCase,
    private val updateCriterionGoalsUseCase: UpdateCriterionGoalsUseCase,

    ) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _photo = MutableLiveData<String>()
    val photo: LiveData<String>
        get() = _photo

    private val _textGoals = MutableLiveData<String>()
    val textGoals: LiveData<String>
        get() = _textGoals

    private val _dataExecution = MutableLiveData<String>()
    val dataExecution: LiveData<String>
        get() = _dataExecution

    private val _quantity = MutableLiveData<Long>()
    val quantity: LiveData<Long>
        get() = _quantity

    private val _unit = MutableLiveData<String>()
    val unit: LiveData<String>
        get() = _unit

    private val _criterion = MutableLiveData<String>()
    val criterion: LiveData<String>
        get() = _criterion

    fun loadGoals(id: Int) {
        scope.launch {
            try {
                val goals = withContext(Dispatchers.IO) {
                    getIdGoalsUseCase.invoke(id)
                }
                _photo.value = goals.photo
                _textGoals.value = goals.textGoals
                _dataExecution.value = goals.dataExecution
                _quantity.value = goals.quantity
                _unit.value = goals.unit
                _criterion.value = goals.criterion
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun saveGoals(
        photo: String,
        textGoals: String,
        dataExecution: String,
        progress: Long,
        quantity: Long,
        unit: String,
        criterion: String
    ) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    saveGoalsUseCase.invoke(
                        photo,
                        textGoals,
                        dataExecution,
                        progress,
                        quantity,
                        unit,
                        criterion
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTextGoals(textGoals: String, id: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    updateTextGoalsUseCase.invoke(textGoals, id)
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
                    updatePhotoGoalsUseCase.invoke(photo, id)
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
                    updateDataExecutionGoalsUseCase.invoke(data, id)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantityGoals(quantity: Long, id: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    updateQuantityGoalsUseCase.invoke(quantity, id)
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
                    updateUnitGoalsUseCase.invoke(unit, id)
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
                    updateCriterionGoalsUseCase.invoke(criterion, id)
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