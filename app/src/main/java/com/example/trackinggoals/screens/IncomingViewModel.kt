package com.example.trackinggoals.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trackinggoals.model.Incoming
import com.example.trackinggoals.model.NoteRepository
import kotlinx.coroutines.*

class IncomingViewModel(
    private val noteRepository: NoteRepository
) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private val _incoming= MutableLiveData<Incoming>()
    val incoming: LiveData<Incoming> = _incoming

    private val _textMessage = MutableLiveData<String>()
    val textMessage: LiveData<String> = _textMessage

    private val _currentData = MutableLiveData<String>()
    val currentData: LiveData<String> = _currentData

    fun loadIncoming(incomingId:Int,noteId:Int,currentDataIn:String) {
        scope.launch {
            try {
                val incomingCurrent = withContext(Dispatchers.IO) {
                    noteRepository.getIncoming(incomingId,noteId,currentDataIn)
                }
                _incoming.value = incomingCurrent
                _textMessage.value = incomingCurrent.textMessages
                _currentData.value = incomingCurrent.currentDataIn
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }



    fun updateIncoming(textMessages: String, idIm: Int) {
        scope.launch {
            try {
                withContext(Dispatchers.IO) {
                    noteRepository.updateIncoming(textMessages, idIm)
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
                    noteRepository.deleteIncoming(incoming)
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



