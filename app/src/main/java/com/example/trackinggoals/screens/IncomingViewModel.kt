package com.example.trackinggoals.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.*
import kotlinx.coroutines.*

class IncomingViewModel(
    private val incomingRepository: IncomingRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _incoming= MutableLiveData<Incoming>()
    val incoming: LiveData<Incoming> = _incoming

    private val _textMessage = MutableLiveData<String>()
    val textMessage: LiveData<String> = _textMessage

    private val _quantity = MutableLiveData<String>()
    val quantity: LiveData<String> = _quantity

    private val _textGoals = MutableLiveData<String>()
    val textGoals: LiveData<String> = _textGoals

    private val _currentData = MutableLiveData<String>()
    val currentData: LiveData<String> = _currentData


    private val _listGoalsLiveData = MutableLiveData<ArrayList<String>>()
    val listGoalsLiveData: LiveData<ArrayList<String>> = _listGoalsLiveData

    private val _selectedGoals = MutableLiveData<Goals>()
    val selectedGoals: LiveData<Goals> = _selectedGoals


    fun loadGoals(){
        scope.launch {
            try {
                val listGoals = withContext(Dispatchers.IO) {
                    incomingRepository.getAllGoals()
                }
                _listGoalsLiveData.value = listGoals

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getSelectedGoals(textGoals: String){

        scope.launch {
            try {
                val goals = withContext(Dispatchers.IO) {
                    incomingRepository.getGoals(textGoals)
                }
                _selectedGoals.value = goals

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun loadIncoming(incomingId:Int,noteId:Int,currentDataIn:String) {
        scope.launch {
            try {
                val incomingCurrent = withContext(Dispatchers.IO) {
                    incomingRepository.getIncoming(incomingId,noteId,currentDataIn)
                }
                _incoming.value = incomingCurrent
                _textMessage.value = incomingCurrent.textMessages
                _currentData.value = incomingCurrent.currentDataIn
                _textGoals.value=incomingCurrent.textGoals
                _quantity.value=incomingCurrent.quantity
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    fun updateTextIncoming(textMessages: String, idIm: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    incomingRepository.updateTextIncoming(textMessages, idIm)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateProgress(progress:String,idGoals:Int){
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    incomingRepository.updateProgress(progress,idGoals)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateQuantity(progress:String,idIm: Int){
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    incomingRepository.updateQuantity(progress,idIm)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTextGoals(textGoals: String,idIm: Int){
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    incomingRepository.updateTextGoals(textGoals, idIm)
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
                    incomingRepository.deleteIncoming(incoming)
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



