package com.example.trackinggoals.presentation.incoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.domain.model.Goals
import com.example.trackinggoals.domain.model.Incoming
import com.example.trackinggoals.domain.usecases.goals.GetTextGoalsUseCase
import com.example.trackinggoals.domain.usecases.goals.UpdateProgressWithoutNewResultGoalsUseCase
import com.example.trackinggoals.domain.usecases.goals.GetAllActiveGoalsForIncoming
import com.example.trackinggoals.domain.usecases.note.*
import kotlinx.coroutines.*

class IncomingViewModel(
    private val getAllGoalsUseCase: GetAllActiveGoalsForIncoming,
    private val getTextGoalsUseCase:GetTextGoalsUseCase,
    private val getIncomingUseCase:GetIncomingUseCase,
    private val updateIncomingNoteUseCase:UpdateIncomingNoteUseCase,
    private val updateProgressWithoutNewResultGoalsUseCase: UpdateProgressWithoutNewResultGoalsUseCase,
    private val updateQuantityNoteUseCase: UpdateQuantityNoteUseCase,
    private val updateTextGoalsNoteUseCase: UpdateTextGoalsNoteUseCase,
    private val deleteIncomingNoteUseCase: DeleteIncomingNoteUseCase,

    ) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _incoming = MutableLiveData<Incoming>()
    val incoming: LiveData<Incoming>
        get() = _incoming

    private val _textMessage = MutableLiveData<String>()
    val textMessage: LiveData<String>
        get() = _textMessage

    private val _quantity = MutableLiveData<String>()
    val quantity: LiveData<String>
        get() = _quantity

    private val _textGoals = MutableLiveData<String>()
    val textGoals: LiveData<String>
        get() = _textGoals

    private val _currentData = MutableLiveData<String>()
    val currentData: LiveData<String>
        get() = _currentData

    private val _listGoalsLiveData = MutableLiveData<ArrayList<String>>()
    val listGoalsLiveData: LiveData<ArrayList<String>>
        get() = _listGoalsLiveData

    private val _selectedGoals = MutableLiveData<Goals>()
    val selectedGoals: LiveData<Goals>
        get() = _selectedGoals


    fun loadGoals() {
        scope.launch {
            try {
                val listGoals = withContext(Dispatchers.IO) {
                    getAllGoalsUseCase.invoke()
                }
                _listGoalsLiveData.value = listGoals

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSelectedGoals(textGoals: String) {

        scope.launch {
            try {
                val goals = withContext(Dispatchers.IO) {
                    getTextGoalsUseCase.invoke(textGoals)
                }
                _selectedGoals.value = goals
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadIncoming(incomingId: Int, noteId: Int, currentDataIn: String) {
        scope.launch {
            try {
                val incomingCurrent = withContext(Dispatchers.IO) {
                    getIncomingUseCase.invoke(incomingId, noteId, currentDataIn)
                }
                _incoming.value = incomingCurrent
                _textMessage.value = incomingCurrent.textMessages
                _currentData.value = incomingCurrent.currentDataIn
                _textGoals.value = incomingCurrent.textGoals
                _quantity.value = incomingCurrent.quantity
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTextIncoming(textMessages: String, idIm: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    updateIncomingNoteUseCase.invoke(textMessages, idIm)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateProgress(progress: String, idGoals: Int, text: String) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    updateProgressWithoutNewResultGoalsUseCase.invoke(progress, idGoals, text)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantity(progress: String, idIm: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    updateQuantityNoteUseCase.invoke(progress, idIm)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTextGoals(textGoals: String, idIm: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    updateTextGoalsNoteUseCase.invoke(textGoals, idIm)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun deleteIncoming(incoming: Incoming) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    deleteIncomingNoteUseCase.invoke(incoming)
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



